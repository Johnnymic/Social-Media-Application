package com.michael.socialmedia.controller;

import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private  final LikeService likeService;


    @GetMapping("/post/{postId}")
   public ResponseEntity<ApiResponse<String>>likePost(@PathVariable("postId") Long postId){
       ApiResponse<String>  apiResponse = new ApiResponse<>(likeService.likePost(postId));
       return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
   }


}
