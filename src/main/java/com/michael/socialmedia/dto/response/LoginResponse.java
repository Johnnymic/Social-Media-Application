package com.michael.socialmedia.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {


    private String userId;



    private String accessToken;


    private String refreshToken;

    public LoginResponse(String userId, String token) {
        this.userId = userId;
        this.accessToken = token;
    }


}
