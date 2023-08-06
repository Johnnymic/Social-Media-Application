package com.michael.socialmedia.service;

import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.EditPostRequest;
import com.michael.socialmedia.dto.request.PostRequest;
import com.michael.socialmedia.dto.response.EditPostResponse;
import com.michael.socialmedia.dto.response.PostResponse;
import com.michael.socialmedia.exceptions.PostNotfoundException;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.PostRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.serviceImpl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImpTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository; // Add the UserRepository mock

    @InjectMocks
    private PostServiceImpl underTest;


    @InjectMocks
    private PostServiceImpl postService;

    private PostRequest postRequest;
    private PostResponse expectedPostResponse;

    @BeforeEach
    public void setUp() {
        // Initialize the test data before each test
        postRequest = PostRequest.builder()
                .Content("Social post")
                .build();

        expectedPostResponse = PostResponse.builder()
                .id(1L)
                .content("Social post")
                .build();
        // Mock the SecurityContext and Authentication objects

    }

    @Test
    public void testThatPostIsCreated() {
        // Given
        final Post post = Post.builder()
                .id(1L)
                .content("Social post")
                .build();

        // Mock the SecurityContext and Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser"); // Provide the authenticated user's email
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock the UserRepository to return a valid user object
        User testUser = new User();
        when(userRepository.findByEmail("testuser")).thenReturn(Optional.of(testUser));

        // When
        when(postRepository.save(ArgumentMatchers.any(Post.class))).thenReturn(post); // Use ArgumentMatchers.any()

        final PostResponse actualPostResponse = underTest.createPost(postRequest);

        // Then
        assertEquals(expectedPostResponse, actualPostResponse, "PostResponse should match");

        // Verify that the userRepository.findByEmail method is called with the correct email
        verify(userRepository).findByEmail("testuser");
    }


    @Test
    public void testCreatePostWithInvalidUser() {
        // Given
        // Mock the SecurityContext and Authentication without setting up UserRepository
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("invaliduser"); // Provide an invalid email
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // When and Then
        // Create a post with an invalid user, it should throw UserNotAuthenticated exception
        assertThrows(UserNotAuthenticated.class, () -> underTest.createPost(postRequest));
    }

    @Test
    public void testViewPost_Success() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        // Set up the postRepository mock to return the post when findById is called
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostResponse expectedResponse = new PostResponse();
        expectedResponse.setId(postId);

        PostResponse actualResponse = postService.viewPost(postId);

        assertEquals(expectedResponse, actualResponse);
        // Verify that postRepository.findById was called exactly once with postId argument
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    public void testViewAllPost_Success() {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        // Set up the postRepository mock to return the list of posts when findAll is called
        when(postRepository.findAll()).thenReturn(posts);

        List<PostResponse> expectedResponses = Arrays.asList(new PostResponse(), new PostResponse());

        List<PostResponse> actualResponses = postService.viewAllPost();

        assertEquals(expectedResponses, actualResponses);
        // Verify that postRepository.findAll was called exactly once
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testDeletePost_Success() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        // Set up the postRepository mock to return the post when findById is called
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        String expectedMessage = "Post Successfully deleted";

        String actualMessage = postService.deletePost(postId);

        assertEquals(expectedMessage, actualMessage);
        // Verify that postRepository.findById and postRepository.delete were called exactly once with postId argument
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    public void testDeletePost_PostNotFound() {
        Long postId = 1L;
        // Set up the postRepository mock to return an empty Optional when findById is called
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When and Then
        // Use assertThrows to verify that deletePost method throws ResourceNotFoundException
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.deletePost(postId));

        // Verify the exception message
        assertEquals("post not found", exception.getMessage());

        // Verify that postRepository.findById was called exactly once with postId argument
        verify(postRepository, times(1)).findById(postId);
        // Verify that postRepository.delete was not called, as the post was not found
        verify(postRepository, never()).delete(any(Post.class));
    }

    @Test
    public void testViewPaginatedPost_Success() {
        // Set up test data
        List<PostResponse> postResponses = Arrays.asList(
                new PostResponse(1L, "Post 1"),
                new PostResponse(2L, "Post 2"),
                new PostResponse(3L, "Post 3")
        );
        int pageNo = 0;
        int pageSize = 3;
        String sortBy = "id";
        boolean isAscending = false;
        // Set up the postRepository mock to return the list of posts when findAll is called
        when(postRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Post(1L, "Post 1"),
                        new Post(2L, "Post 2"),
                        new Post(3L, "Post 3")
                )
        );

        Page<PostResponse> expectedPage = new PageImpl<>(postResponses.subList(0, 3));

        Page<PostResponse> actualPage = postService.viewPaginatedPost(pageNo, pageSize, sortBy, isAscending);

        // Compare the content of the Page objects

        // Verify other properties of the Page objects
        assertEquals(expectedPage.getNumber(), actualPage.getNumber(), "Page number should match");
        assertEquals(expectedPage.getSize(), actualPage.getSize(), "Page size should match");
        assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements(), "Total elements should match");
        assertEquals(expectedPage.getTotalPages(), actualPage.getTotalPages(), "Total pages should match");
        // ... add more assertions if needed
    }


    @Test
    public void testEditPost_Success() {
        Long postId = 1L;
        EditPostRequest editPostRequest = new EditPostRequest();
        editPostRequest.setContent("Updated content");
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        // Set up the user name you want to use for testing
        when(authentication.getName()).thenReturn("testuser");

        // Set the Authentication object in the SecurityContext
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Set the SecurityContext as the current context
        SecurityContextHolder.setContext(securityContext);

        // Set up the userRepository mock to return a user when findByEmail is called
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Set up the postRepository mock to return the post when findById is called
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post()));

        // Set up the postRepository mock to return the updated post when save is called
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EditPostResponse expectedResponse = new EditPostResponse();
        expectedResponse.setContent("Updated content");

        EditPostResponse actualResponse = postService.editPost(postId, editPostRequest);

        assertEquals(expectedResponse, actualResponse);
        // Verify that userRepository.findByEmail and postRepository.findById were called exactly once
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(postRepository, times(1)).findById(postId);
        // Verify that postRepository.save was called exactly once with the updated post
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testEditPost_PostNotFound() {
        Long postId = 1L;
        EditPostRequest editPostRequest = new EditPostRequest();
        editPostRequest.setContent("Updated content");

        // Set up the userRepository mock to return a user when findByEmail is called
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Set up the postRepository mock to return an empty Optional when findById is called
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // When and Then
        // Attempt to edit a post that doesn't exist, it should throw PostNotfoundException
        assertThrows(PostNotfoundException.class, () -> postService.editPost(postId, editPostRequest));
        // Verify that userRepository.findByEmail and postRepository.findById were called exactly once
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(postRepository, times(1)).findById(postId);
        // Verify that postRepository.save was not called since the post was not found
        verify(postRepository, never()).save(any(Post.class));
    }

}
