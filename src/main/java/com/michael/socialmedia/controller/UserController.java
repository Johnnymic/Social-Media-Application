package com.michael.socialmedia.controller;


import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentOnPostRequest;
import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.CommentOnPostResponse;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;
import com.michael.socialmedia.service.UserService;
import com.michael.socialmedia.utils.UserPage;
import com.michael.socialmedia.utils.UserSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final  UserService userService;


  @PostMapping(value = "upload/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ApiResponse<Object>>uploadProfilePic(@RequestPart MultipartFile multipartFile) throws IOException {
      ApiResponse<Object> apiResponse =new ApiResponse<>(userService.uploadProfilePic(multipartFile));
      return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);

  }


  @PutMapping("/edit/user/profile")
  public ResponseEntity<ApiResponse<EditUserProfileResponse>> editUserProfile( @RequestBody  EditUserProfileRequest editUserProfileRequest){
        ApiResponse<EditUserProfileResponse> apiResponse = new ApiResponse<>(userService.editUserProfile(editUserProfileRequest));
      return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

  }

  @GetMapping("/view/user/profile/{userId}")
  public ResponseEntity<ApiResponse<UserProfileResponse>> viewUserProfile(@PathVariable("userId") Long userId){
      ApiResponse<UserProfileResponse> apiResponse = new ApiResponse<>(userService.viewUserProfile(userId));
      return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

  }

  @GetMapping("/view/users/profile")
  public ResponseEntity<ApiResponse<List<UserProfileResponse>>> viewAllUserProfile(){
      ApiResponse<List<UserProfileResponse>> apiResponse = new ApiResponse<>(userService.viewAllUserProfile());
      return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

  }

  @DeleteMapping("/delete/users/profile/{userId}")
  public ResponseEntity<ApiResponse<String>> viewDeleteUserProfile(@PathVariable("userId") Long userId){
      ApiResponse<String> apiResponse = new ApiResponse<>(userService.deleteUserProfile(userId));
      return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @PostMapping("/{followerEmail}/follower/{followingEmail}")
   public ResponseEntity<ApiResponse<String>> followerUser(@PathVariable("followerEmail") String followerUser, @PathVariable("followingEmail") String followingUser){
      ApiResponse<String>apiResponse = new ApiResponse<>(userService.toggleFollow(followerUser,followingUser));
      return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @PostMapping("/comment/post")
  public ResponseEntity<ApiResponse<CommentOnPostResponse>> commentOnPost(@RequestBody CommentOnPostRequest comment){
      ApiResponse<CommentOnPostResponse>apiResponse = new ApiResponse<>(userService.commentOnPost(comment));
      return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @PostMapping("/comment/{commentId}/remove/post/{postId}")
  public ResponseEntity<ApiResponse<String>> removeCommentFromPost(@PathVariable("commentId") Long commentId, @PathVariable("postId") Long postId){
      ApiResponse<String>apiResponse = new ApiResponse<>(userService.removeCommentFromPost(commentId,postId));
      return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @GetMapping("/filter/search")
  public  ResponseEntity<ApiResponse <Page<User>>>filterAllUser(UserPage userPage, UserSearchCriteria userSearchCriteria){

      ApiResponse<Page<User>> apiResponse =new ApiResponse<>(userService.filterAndSearch(userPage,userSearchCriteria));

      return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
  }



}
