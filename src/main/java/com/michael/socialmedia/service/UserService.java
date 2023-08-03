package com.michael.socialmedia.service;

import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;

import java.util.List;

public interface UserService {
    EditUserProfileResponse editUserProfile(EditUserProfileRequest editUserProfileRequest);

   UserProfileResponse viewUserProfile(Long userId);

   List<UserProfileResponse> viewAllUserProfile();

    String  deleteUserProfile(Long userId);
}
