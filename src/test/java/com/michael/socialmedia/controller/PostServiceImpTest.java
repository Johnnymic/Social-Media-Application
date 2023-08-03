package com.michael.socialmedia.controller;

import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.dto.request.PostRequest;
import com.michael.socialmedia.dto.response.PostResponse;
import com.michael.socialmedia.repository.PostRepository;
import com.michael.socialmedia.service.PostService;
import com.michael.socialmedia.service.serviceImpl.PostServiceImpl;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class )

public class PostServiceImpTest {

    @Mock
    private   PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl underTest;



    @Test
    public void testThatPostIsCreated(){
        final Post post = Post.builder()
                .id(1L)
                .content("Social post")
                .build();

        final PostRequest postRequest = PostRequest.builder()
                .id(1L)
                .Content("Social post")
                .build();

        final PostResponse postResponse = PostResponse.builder()
                .id(1L)
                .content("Social post")
                .build();


        when(postRepository.save(eq(post))).thenReturn(post);
         final PostResponse postRequest1 = underTest.createPost(postRequest);
          assertEquals(postResponse,postRequest1);

    }


}
