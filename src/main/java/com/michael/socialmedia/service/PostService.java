package com.michael.socialmedia.service;

import com.michael.socialmedia.dto.request.EditPostRequest;
import com.michael.socialmedia.dto.request.PostRequest;
import com.michael.socialmedia.dto.response.EditPostResponse;
import com.michael.socialmedia.dto.response.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest postRequest);

    PostResponse viewPost(Long postId);

   List<PostResponse> viewAllPost();
    String deletePost(Long postId);

    Page<PostResponse> viewPaginatedPost(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);

    EditPostResponse editPost(Long postId, EditPostRequest editPostRequest);
}
