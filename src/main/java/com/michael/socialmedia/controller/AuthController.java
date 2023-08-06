package com.michael.socialmedia.controller;

import com.michael.socialmedia.dto.request.LoginRequest;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.LoginResponse;
import com.michael.socialmedia.dto.response.RegisterResponse;
import com.michael.socialmedia.exceptions.UserNotFoundException;
import com.michael.socialmedia.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register/user")
    private ResponseEntity<ApiResponse<RegisterResponse>>registerUser(@RequestBody RegisterRequest registerRequest){
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(authenticationService.registerUser(registerRequest));
        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login/user")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authenticationService.loginUser(loginRequest);
            ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(loginResponse);
            return ResponseEntity.ok(apiResponse);
        } catch (UserNotFoundException e) {
            ApiResponse<LoginResponse> errorResponse = new ApiResponse<>("User not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }



}
