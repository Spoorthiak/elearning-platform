package com.elearning.serviceimpl;

import com.elearning.entity.Course;
import com.elearning.entity.Enrollment;
import com.elearning.entity.User;
import com.elearning.repository.CourseRepository;
import com.elearning.repository.EnrollmentRepository;
import com.elearning.repository.UserRepository;
import com.elearning.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class PaymentServiceImpl implements PaymentService {

    private final RazorpayClient razorpayClient;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    
    @Value("${razorpay.key.secret}")
    private String keySecret;

    public PaymentServiceImpl(RazorpayClient razorpayClient,
            CourseRepository courseRepository,
            UserRepository userRepository,
            EnrollmentRepository enrollmentRepository) {
				this.razorpayClient = razorpayClient;
				this.courseRepository = courseRepository;
				this.userRepository = userRepository;
				this.enrollmentRepository = enrollmentRepository;
    		}

    @Override
    public JSONObject createOrder(Long courseId, String username)
            throws RazorpayException {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Razorpay amount is in paise
        int amountInPaise = (int) Math.round(course.getPrice() * 100);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "course_" + courseId + "_" + user.getId());

        return razorpayClient.orders.create(orderRequest).toJson();
    }

    @Override
    public boolean verifyPayment(Long courseId,
                                 String username,
                                 String orderId,
                                 String paymentId,
                                 String signature) {

        try {
        	boolean verified = com.razorpay.Utils.verifyPaymentSignature(
        	        new JSONObject()
        	                .put("razorpay_order_id", orderId)
        	                .put("razorpay_payment_id", paymentId)
        	                .put("razorpay_signature", signature),
        	        getKeySecret()
        	);
        	
            if (verified) {

                User student = userRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException("Student not found"));

                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() ->
                                new RuntimeException("Course not found"));

                boolean alreadyEnrolled =
                        enrollmentRepository.existsByStudentIdAndCourseId(
                                student.getId(),
                                course.getId()
                        );

                if (!alreadyEnrolled) {

                    Enrollment enrollment = new Enrollment();

                    enrollment.setStudent(student);
                    enrollment.setCourse(course);
                    enrollment.setEnrolledAt(LocalDateTime.now());
                    enrollment.setStatus("ACTIVE");

                    enrollmentRepository.save(enrollment);
                }
            }

            return verified;

        } catch (Exception e) {
            return false;
        }
    }

    private String getKeySecret() {
        return keySecret;
    }
}