package com.michael.socialmedia.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUserProfileResponse {
    private  String username;

    private String password;

    private  String email;


    private  String profilePic;
}
