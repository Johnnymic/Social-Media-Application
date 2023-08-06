package com.michael.socialmedia;

import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.LoginRequest;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.LoginResponse;
import com.michael.socialmedia.dto.response.RegisterResponse;
import com.michael.socialmedia.enums.Role;

public class UserData {
    private UserData() {};

    public static RegisterRequest buildRegisterUser() {
        return RegisterRequest.builder()
                .username("jeffHardy")
                .email("jeff@gmail.com")
                .password("1234")
                .build();
    }
    public static RegisterResponse buildUserRegisterResponse() {
        return RegisterResponse.builder()
                .username("jeffHardy")
                .email("jeff@gmail.com")
                .build();
    }

    public static LoginRequest buildLoginRequest() {
        return LoginRequest.builder()
                .email("jeffHardy@gamil.com")
                .password("password123")
                .build();
    }

    public static LoginResponse buildLoginResponse() {
        return LoginResponse.builder()
                .refreshToken("efhkuegfwufeepuaHWWAETHGV")
                .refreshToken("yryrygwgdshflylfqrl")
                .build();

    }
}
