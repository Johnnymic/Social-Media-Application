package com.michael.socialmedia.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.socialmedia.UserData;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.RegisterResponse;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.AuthenticationService;
import com.michael.socialmedia.service.serviceImpl.AuthenticationServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.status;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
public class AuthControllerTest {

   @Autowired
   private MockMvc mockMvc;
    @InjectMocks
    private AuthenticationService authenticationService;

   @Mock
   private UserRepository userRepository;

    @Test
    public void testUserRegistration() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterResponse signUpResponse = UserData.buildUserRegisterResponse();
        RegisterRequest signUpRequest = UserData.buildRegisterUser();


        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>("Registration successful", LocalDateTime.now(), signUpResponse);


       when(authenticationService.registerUser(signUpRequest)).thenReturn(apiResponse.getData());

        mockMvc.perform(post("/api/v1/auth/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk()).andReturn();
        String  responseContent = mvcResult.getResponse().getContentAsString();
        ApiResponse<RegisterResponse> actualResponse =
                objectMapper.readValue(responseContent, new TypeReference<>(){

                });
        assertEquals(apiResponse.getData().getEmail(),actualResponse.getData().getEmail());




    }



}
/*
        mockMvc.perform(post("/api/fashion-blog/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userSignupDTO))) // set the request body
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());

                      MvcResult mvcResult = mockMvc.perform(post("/api/fashion-blog/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userSignupDTO)))
                .andExpect(status().isOk())
                .andReturn();
 */