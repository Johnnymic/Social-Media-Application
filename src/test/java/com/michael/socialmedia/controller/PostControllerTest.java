package com.michael.socialmedia.controller;

import com.michael.socialmedia.dto.request.EditPostRequest;
import com.michael.socialmedia.dto.request.PostRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.EditPostResponse;
import com.michael.socialmedia.dto.response.PostResponse;
import com.michael.socialmedia.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    public void testCreatePost_Success() {
        // Given
        PostRequest postRequest = new PostRequest();
        // Set up the mock response from the service
        when(postService.createPost(any(PostRequest.class))).thenReturn(new PostResponse(1L, "Test Post"));

        // When
        ResponseEntity<ApiResponse<PostResponse>> response = postController.createPost(postRequest);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getData().getId());
        assertEquals("Test Post", response.getBody().getData().getContent());

        // Verify that the service method was called once
        verify(postService, times(1)).createPost(any(PostRequest.class));
    }

    @Test
    public void testViewPost_Success() {
        // Given
        Long postId = 1L;
        // Set up the mock response from the service
        when(postService.viewPost(postId)).thenReturn(new PostResponse(postId, "Test Post"));

        // When
        ResponseEntity<ApiResponse<PostResponse>> response = postController.viewPost(postId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(postId, response.getBody().getData().getId());
        assertEquals("Test Post", response.getBody().getData().getContent());

        // Verify that the service method was called once
        verify(postService, times(1)).viewPost(postId);
    }

    @Test
    public void testViewAllPost_Success() {
        // Given
        // Set up the mock response from the service
        when(postService.viewAllPost()).thenReturn(Arrays.asList(
                new PostResponse(1L, "Test Post 1"),
                new PostResponse(2L, "Test Post 2")
        ));

        // When
        ResponseEntity<ApiResponse<List<PostResponse>>> response = postController.viewAllPost();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getData().size());

        // Verify that the service method was called once
        verify(postService, times(1)).viewAllPost();
    }

    @Test
    public void testEditPost_Success() {
        // Given
        Long postId = 1L;
        EditPostRequest editPostRequest = new EditPostRequest();
        editPostRequest.setContent("Updated Test Post");
        // Set up the mock response from the service
        EditPostResponse mockEditPostResponse = new EditPostResponse(postId, "Updated Test Post");
        when(postService.editPost(postId, editPostRequest)).thenReturn(mockEditPostResponse);

        // When
        ResponseEntity<ApiResponse<EditPostResponse>> response = postController.editPost(postId, editPostRequest);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockEditPostResponse, response.getBody().getData());

        // Verify that the service method was called once
        verify(postService, times(1)).editPost(postId, editPostRequest);
    }


    @Test
    public void testDeletePost_Success() {
        // Given
        Long postId = 1L;
        // Set up the mock response from the service
        when(postService.deletePost(postId)).thenReturn("Post Successfully deleted");

        // When
        ResponseEntity<ApiResponse<String>> response = postController.deletePost(postId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Post Successfully deleted", response.getBody().getData());

        // Verify that the service method was called once
        verify(postService, times(1)).deletePost(postId);
    }

    @Test
    public void testViewPaginatedPosts_Success() {
        // Given
        // Set up the mock response from the service
        when(postService.viewPaginatedPost(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(mock(Page.class));

        // When
        ResponseEntity<ApiResponse<Page<PostResponse>>> response = postController.viewPaginatedPosts(0, 10, "id", false);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verify that the service method was called once
        verify(postService, times(1)).viewPaginatedPost(anyInt(), anyInt(), anyString(), anyBoolean());
    }
}
