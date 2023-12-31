package com.michael.socialmedia.dto.request;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private Long id;
    private  String username;

    private String password;

    private  String email;


    private  String profilePic;
}
