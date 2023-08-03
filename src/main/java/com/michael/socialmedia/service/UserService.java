package com.michael.socialmedia.service;

import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentOnPostRequest;
import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.CommentOnPostResponse;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;
import com.michael.socialmedia.utils.UserPage;
import com.michael.socialmedia.utils.UserSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    EditUserProfileResponse editUserProfile(EditUserProfileRequest editUserProfileRequest);

   UserProfileResponse viewUserProfile(Long userId);

   List<UserProfileResponse> viewAllUserProfile();

    String  deleteUserProfile(Long userId);

    String toggleFollow(String followerUser, String followingUser);


    CommentOnPostResponse commentOnPost(CommentOnPostRequest comment);

    String removeCommentFromPost(Long commentId, Long postId);

    Page<User> filterAndSearch(UserPage userPage, UserSearchCriteria userSearchCriteria);

    Object uploadProfilePic(MultipartFile multipartFile) throws IOException;
}
