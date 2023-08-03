package com.michael.socialmedia.controller;

import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private  final LikeService likeService;

   public ResponseEntity<ApiResponse<String>>likePost(@PathVariable("postId") Long postId, @RequestParam String userEmail){
       ApiResponse<String>  apiResponse = new ApiResponse<>(likeService.likePost(postId,userEmail));
       return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
   }


}
