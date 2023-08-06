package com.michael.socialmedia.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {
    private Long userId;
    private  String username;

    private  String email;

    private  String profilePic;
}
