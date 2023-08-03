package com.michael.socialmedia.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {




    private String accessToken;


    private String refreshToken;
}
