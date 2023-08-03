package com.michael.socialmedia.controller;

import com.michael.socialmedia.dto.request.CommentRequest;
import com.michael.socialmedia.dto.request.EditCommentRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.CommentResponse;
import com.michael.socialmedia.dto.response.EditCommentResponse;
import com.michael.socialmedia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private  final CommentService commentService;

    @PostMapping("/create/new/comment")
    public ResponseEntity<ApiResponse<CommentResponse>>createComment(@RequestBody CommentRequest comment){
        ApiResponse<CommentResponse> apiResponse = new ApiResponse<>(commentService.createNewComment(comment));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @GetMapping("/view/comment/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>>viewComment(@PathVariable("commentId") Long commentId){
        ApiResponse<CommentResponse> apiResponse = new ApiResponse<>(commentService.viewComment(commentId));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    @GetMapping("/view/comment")
    public ResponseEntity<ApiResponse<List<CommentResponse>>>viewAllComment(){
        ApiResponse<List<CommentResponse>> apiResponse = new ApiResponse<>(commentService.viewAllComment());
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PutMapping("/edit/comment/{commentId}")
    public ResponseEntity<ApiResponse<EditCommentResponse>>editComment(@PathVariable("commentId") Long commentId, @RequestBody EditCommentRequest comment ){
        ApiResponse<EditCommentResponse> apiResponse = new ApiResponse<>(commentService.editComment(commentId,comment));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }


    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<String>>deleteComment(@PathVariable("commentId") Long commentId){
        ApiResponse<String> apiResponse = new ApiResponse<>(commentService.deleteComment(commentId));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CommentResponse>>>viewAllCommentByPagination(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "16") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy

    ){
        ApiResponse <Page<CommentResponse>> apiResponse = new ApiResponse<>(commentService.viewCommentsByPagination(pageNo,pageSize,sortBy));
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }









}
