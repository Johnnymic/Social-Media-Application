package com.michael.socialmedia.controller;

import com.michael.socialmedia.dto.request.EditPostRequest;
import com.michael.socialmedia.dto.request.PostRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.EditPostResponse;
import com.michael.socialmedia.dto.response.PostResponse;
import com.michael.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/add/post")
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody PostRequest postRequest){
        ApiResponse<PostResponse> apiResponse = new ApiResponse<>(postService.createPost(postRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @GetMapping("/view/post/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>>viewPost(@PathVariable("postId")Long postId){
        ApiResponse<PostResponse> apiResponse = new ApiResponse<>(postService.viewPost(postId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/view/all/post")
    public ResponseEntity<ApiResponse<List<PostResponse>>>viewAllPost(){
        ApiResponse <List<PostResponse>> apiResponse = new ApiResponse<>(postService.viewAllPost());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PutMapping("/edit/post/{postId}")
    public ResponseEntity<ApiResponse<EditPostResponse>>editPost(@PathVariable("postId")Long postId, @RequestBody EditPostRequest editPostRequest){
        ApiResponse<EditPostResponse> apiResponse = new ApiResponse<>(postService.editPost(postId,editPostRequest));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/delete/post/{postId}")
    public ResponseEntity<ApiResponse<String>>deletePost(@PathVariable("postId")Long postId){
        ApiResponse <String> apiResponse = new ApiResponse<>(postService.deletePost(postId));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/view/post/paginated")
    public ResponseEntity<ApiResponse<Page<PostResponse>>>viewPaginatedPosts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAscending
    ){
        ApiResponse<Page<PostResponse>>apiResponse = new ApiResponse<>(postService.viewPaginatedPost(pageNo,pageSize,sortBy,isAscending));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }


}
