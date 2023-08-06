package com.michael.socialmedia.controller;

import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentOnPostRequest;
import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.CommentOnPostResponse;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;
import com.michael.socialmedia.service.UserService;
import com.michael.socialmedia.utils.UserPage;
import com.michael.socialmedia.utils.UserSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testUploadProfilePic() throws IOException {
        // Mocking the MultipartFile
        MultipartFile multipartFile = mock(MultipartFile.class);

        // Mocking the service method
        String expectedResponse = "Profile pic uploaded successfully";
        when(userService.uploadProfilePic(multipartFile)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<Object>> responseEntity = userController.uploadProfilePic(multipartFile);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testEditUserProfile() {
        // Mocking the EditUserProfileRequest
        EditUserProfileRequest editUserProfileRequest = new EditUserProfileRequest();
        // Set the request properties

        // Mocking the service method
        EditUserProfileResponse expectedResponse = new EditUserProfileResponse();
        // Set the response properties
        when(userService.editUserProfile(editUserProfileRequest)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<EditUserProfileResponse>> responseEntity = userController.editUserProfile(editUserProfileRequest);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testViewUserProfile() {
        // Mocking the userId
        Long userId = 1L;

        // Mocking the service method
        UserProfileResponse expectedResponse = new UserProfileResponse();
        // Set the response properties
        when(userService.viewUserProfile(userId)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<UserProfileResponse>> responseEntity = userController.viewUserProfile(userId);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testViewAllUserProfile() {
        // Mocking the service method
        List<UserProfileResponse> expectedResponse = new ArrayList<>();
        // Add some mock UserProfileResponse objects to the list
        when(userService.viewAllUserProfile()).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<List<UserProfileResponse>>> responseEntity = userController.viewAllUserProfile();

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUserProfile() {
        // Mocking the userId
        Long userId = 1L;

        // Mocking the service method
        String expectedResponse = "User profile deleted successfully";
        when(userService.deleteUserProfile(userId)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<String>> responseEntity = userController.viewDeleteUserProfile(userId);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testFollowerUser() {
        // Mocking the user IDs
        Long followerUserId = 1L;
        Long followingUserId = 2L;

        // Mocking the service method
        String expectedResponse = "User with ID 1 is now following user with ID 2";
        when(userService.toggleFollow(followerUserId, followingUserId)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<String>> responseEntity = userController.followerUser(followerUserId, followingUserId);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCommentOnPost() {
        // Mocking the CommentOnPostRequest
        CommentOnPostRequest commentOnPostRequest = new CommentOnPostRequest();
        // Set the request properties

        // Mocking the service method
        CommentOnPostResponse expectedResponse = new CommentOnPostResponse();
        // Set the response properties
        when(userService.commentOnPost(commentOnPostRequest)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<CommentOnPostResponse>> responseEntity = userController.commentOnPost(commentOnPostRequest);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testRemoveCommentFromPost() {
        // Mocking the commentId and postId
        Long commentId = 1L;
        Long postId = 2L;

        // Mocking the service method
        String expectedResponse = "Comment with ID 1 removed from post with ID 2";
        when(userService.removeCommentFromPost(commentId, postId)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<String>> responseEntity = userController.removeCommentFromPost(commentId, postId);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testFilterAllUser() {
        // Mocking the UserPage and UserSearchCriteria
        UserPage userPage = new UserPage();
        // Set the page properties

        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        // Set the search criteria properties

        // Mocking the service method
        Page<User> expectedResponse = mock(Page.class);
        when(userService.filterAndSearch(userPage, userSearchCriteria)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<ApiResponse<Page<User>>> responseEntity = userController.filterAllUser(userPage, userSearchCriteria);

        // Asserting the response
        assertEquals(expectedResponse, responseEntity.getBody().getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
