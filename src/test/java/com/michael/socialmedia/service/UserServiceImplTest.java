package com.michael.socialmedia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cloudinary.Cloudinary;
import com.michael.socialmedia.Config.CloudinaryConfig;
import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.Follow;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentOnPostRequest;
import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.CommentOnPostResponse;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;
import com.michael.socialmedia.exceptions.CommentNotFoundException;
import com.michael.socialmedia.exceptions.PostNotfoundException;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.*;
import com.michael.socialmedia.service.serviceImpl.UserServiceImpl;
import com.michael.socialmedia.utils.EmailUtils;
import com.michael.socialmedia.utils.Mapper;
import com.michael.socialmedia.utils.UserPage;
import com.michael.socialmedia.utils.UserSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private FollowerRepository followerRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserCriteriaRepository userCriteriaRepository;
    @Mock
    private CloudinaryConfig cloudinaryConfig;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void editUserProfile_ValidRequest_ReturnsEditUserProfileResponse() {
//        // Arrange
//        EditUserProfileRequest editUserProfileRequest = new EditUserProfileRequest();
//        editUserProfileRequest.setUsername("TestUser");
//        editUserProfileRequest.setProfilePic("https://example.com/test.jpg");
//
//        User loginUser = new User();
//        loginUser.setEmail("test@example.com");
//        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(loginUser));
//
//        // Act
//        EditUserProfileResponse result = userService.editUserProfile(editUserProfileRequest);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("TestUser", result.getUsername());
//        assertEquals("https://example.com/test.jpg", result.getProfilePic());
//    }


//    @Test
//    void editUserProfile_UserNotAuthenticated_ThrowsUserNotAuthenticatedException() {
//        // Arrange
//        EditUserProfileRequest editUserProfileRequest = new EditUserProfileRequest();
//        editUserProfileRequest.setUsername("TestUser");
//        editUserProfileRequest.setProfilePic("https://example.com/test.jpg");
//
//        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(UserNotAuthenticated.class, () -> userService.editUserProfile(editUserProfileRequest));
//    }

//    @Test
//    void viewUserProfile_ValidUserId_ReturnsUserProfileResponse() {
//        // Arrange
//        Long userId = 1L;
//        User user = new User();
//        user.setId(userId);
//        user.setUsername("TestUser");
//        user.setEmail("test@example.com");
//        user.setProfilePic("https://example.com/test.jpg");
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        // Act
//        UserProfileResponse result = userService.viewUserProfile(userId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(userId, result.getUserId());
//        assertEquals("TestUser", result.getUsername());
//        assertEquals("test@example.com", result.getEmail());
//        assertEquals("https://example.com/test.jpg", result.getProfilePic());
//    }

    @Test
    void viewUserProfile_InvalidUserId_ThrowsResourceNotFoundException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.viewUserProfile(userId));
    }

//    @Test
//    void viewAllUserProfile_ReturnsListOfUserProfileResponse() {
//        // Arrange
//        List<User> users = new ArrayList<>();
//        User user1 = new User();
//        user1.setId(1L);
//        user1.setUsername("TestUser1");
//        user1.setEmail("test1@example.com");
//        user1.setProfilePic("https://example.com/test1.jpg");
//        users.add(user1);
//
//        User user2 = new User();
//        user2.setId(2L);
//        user2.setUsername("TestUser2");
//        user2.setEmail("test2@example.com");
//        user2.setProfilePic("https://example.com/test2.jpg");
//        users.add(user2);
//
//        when(userRepository.findAll()).thenReturn(users);
//
//        // Act
//        List<UserProfileResponse> result = userService.viewAllUserProfile();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.size());
//
//        // Assert individual UserProfileResponse objects
//        UserProfileResponse userProfile1 = result.get(0);
//        assertEquals(1L, userProfile1.getUserId());
//        assertEquals("TestUser1", userProfile1.getUsername());
//        assertEquals("test1@example.com", userProfile1.getEmail());
//        assertEquals("https://example.com/test1.jpg", userProfile1.getProfilePic());
//
//        UserProfileResponse userProfile2 = result.get(1);
//        assertEquals(2L, userProfile2.getUserId());
//        assertEquals("TestUser2", userProfile2.getUsername());
//        assertEquals("test2@example.com", userProfile2.getEmail());
//        assertEquals("https://example.com/test2.jpg", userProfile2.getProfilePic());
//    }

//    @Test
//    void deleteUserProfile_ValidUserId_DeletesUserProfileSuccessfully() {
//        // Arrange
//        Long userId = 1L;
//        User user = new User();
//        user.setId(userId);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        // Act
//        String result = userService.deleteUserProfile(userId);
//
//        // Assert
//        assertEquals("user profile deleted successful", result);
//        verify(userRepository, times(1)).delete(user);
//    }

    @Test
    void deleteUserProfile_InvalidUserId_ThrowsResourceNotFoundException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUserProfile(userId));
        verify(userRepository, never()).delete(any(User.class));
    }

//    @Test
//    void toggleFollow_UserAndFollowingUsersExistAndUserAlreadyFollows_FollowRelationshipDeletedSuccessfully() {
//        // Arrange
//        Long followerEmailId = 1L;
//        Long followingEmailId = 2L;
//        String emailFromContent = "test@example.com";
//
//        User followerUser = new User();
//        followerUser.setId(followerEmailId);
//        followerUser.setEmail(emailFromContent);
//
//        User followingUser = new User();
//        followingUser.setId(followingEmailId);
//
//        Follow follower = new Follow();
//        follower.setFollower(followerUser);
//        follower.setFollowing(followingUser);
//
//        when(userRepository.findByEmailAndId(eq(emailFromContent), eq(followerEmailId))).thenReturn(followerUser);
//        when(userRepository.findByEmailAndId(eq(emailFromContent), eq(followingEmailId))).thenReturn(followingUser);
//        when(followerRepository.findByFollowerAndFollowing(any(User.class), any(User.class))).thenReturn(follower);
//
//        // Act
//        String result = userService.toggleFollow(followerEmailId, followingEmailId);
//
//    }

}
