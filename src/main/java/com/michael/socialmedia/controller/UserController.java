package com.michael.socialmedia.controller;

import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;
import com.michael.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final  UserService userService;

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







}
