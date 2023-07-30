package com.michael.socialmedia.integrationTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.socialmedia.UserData;
import com.michael.socialmedia.controller.AuthController;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.LoginRequest;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.LoginResponse;
import com.michael.socialmedia.dto.response.RegisterResponse;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.AuthenticationService;
import com.michael.socialmedia.service.serviceImpl.AuthenticationServiceImpl; // Import the concrete implementation
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity; // Import the ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)

public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationService authenticationService; // Mock the service

    @InjectMocks
    private AuthController authController; // Inject the controller

    private  ObjectMapper objectMapper ;
    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testUserRegistration() throws Exception {
        // Arrange
        RegisterResponse signUpResponse = UserData.buildUserRegisterResponse();
        RegisterRequest signUpRequest = UserData.buildRegisterUser();

        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>("Processed successful", signUpResponse);
        when(authenticationService.registerUser(signUpRequest)).thenReturn(signUpResponse);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Processed successful"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andReturn();

        // Assert
        String responseContent = mvcResult.getResponse().getContentAsString();
        ApiResponse<RegisterResponse> actualResponse = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(apiResponse.getData().getEmail(), actualResponse.getData().getEmail());
    }

    // AuthControllerTest class
// ... (existing code)


    public void testUserLogin() throws Exception {
        // Arrange


        // You should create a test user in the UserRepository for this test case
        // For this example, let's assume the test user is created with the following details
        String email = "testuser@example.com";
        String password = "testpassword";
        String encodedPassword = "encoded_testpassword";

        String accessToken = "test_access_token";
        String refreshToken = "test_refresh_token";// Encode the password using the passwordEncoder bean
        LoginRequest loginRequest = new LoginRequest(email, password);
        User testUser = new User();
        testUser.setEmail(email);
        testUser.setPassword(password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(password,encodedPassword )).thenReturn(true);

       // AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);
        // Create a controller instance with the mock service
        AuthController authController = new AuthController(authenticationService);

        when(authenticationService.loginUser(loginRequest)).thenReturn(
                new LoginResponse(accessToken, refreshToken)
        );

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        String responseContent = mvcResult.getResponse().getContentAsString();
        ApiResponse<LoginResponse> apiResponse = objectMapper.readValue(responseContent, new TypeReference<>() {});

        // Check if the response contains an access token and refresh token
        assertNotNull(apiResponse.getData());
        assertNotNull(apiResponse.getData().getAccessToken());
        assertNotNull(apiResponse.getData().getRefreshToken());
    }

}
