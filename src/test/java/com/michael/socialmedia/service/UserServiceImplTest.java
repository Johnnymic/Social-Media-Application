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




    @Test
    void viewUserProfile_InvalidUserId_ThrowsResourceNotFoundException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.viewUserProfile(userId));
    }



    @Test
    void deleteUserProfile_InvalidUserId_ThrowsResourceNotFoundException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUserProfile(userId));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void toggleFollow_UserAndFollowingUsersExistAndUserAlreadyFollows_FollowRelationshipDeletedSuccessfully() {
        // Arrange
        Long followerEmailId = 1L;
        Long followingEmailId = 2L;
        String emailFromContent = "test@example.com";

        User followerUser = new User();
        followerUser.setId(followerEmailId);
        followerUser.setEmail(emailFromContent);

        User followingUser = new User();
        followingUser.setId(followingEmailId);

        Follow follower = new Follow();
        follower.setFollower(followerUser);
        follower.setFollowing(followingUser);

        when(userRepository.findByEmailAndId(eq(emailFromContent), eq(followerEmailId))).thenReturn(Optional.of(followerUser));
        when(userRepository.findByEmailAndId(eq(emailFromContent), eq(followingEmailId))).thenReturn(Optional.of(followingUser));
        when(followerRepository.findByFollowerAndFollowing(any(User.class), any(User.class))).thenReturn(follower);

        // Act
        String result = userService.toggleFollow(followerEmailId, followingEmailId);

    }

}
