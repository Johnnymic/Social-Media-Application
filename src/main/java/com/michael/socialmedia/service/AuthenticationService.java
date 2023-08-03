package com.michael.socialmedia.service;

import com.michael.socialmedia.dto.request.LoginRequest;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.LoginResponse;
import com.michael.socialmedia.dto.response.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse registerUser(RegisterRequest registerRequest);

    LoginResponse loginUser(LoginRequest loginRequest);
}
