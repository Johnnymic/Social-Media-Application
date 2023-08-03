package com.michael.socialmedia.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditUserProfileRequest {
    private  String username;

    private String password;

    private  String email;


    private  String profilePic;

}
