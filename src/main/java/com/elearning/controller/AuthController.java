package com.elearning.controller;

import com.elearning.dto.UserDTO;
import com.elearning.entity.User;
import com.elearning.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.elearning.dto.LoginDTO;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.elearning.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody UserDTO userDTO) {

        try {

            User user = userService.registerUser(
                    userDTO.getUsername(),
                    userDTO.getPassword()
            );

            return new ResponseEntity<>(
                    "User registered successfully!",
                    HttpStatus.CREATED
            );

        } catch (RuntimeException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
            )
        );

        User user = userService.findByUsername(loginDTO.getUsername());

        String token = jwtService.generateToken(
            user.getUsername(),
            user.getRole()
        );

        return ResponseEntity.ok(token);
    }
    
    
}