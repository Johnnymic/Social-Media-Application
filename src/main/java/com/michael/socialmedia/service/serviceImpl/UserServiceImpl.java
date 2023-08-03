package com.michael.socialmedia.service.serviceImpl;

import com.michael.socialmedia.domain.Follow;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.FollowerRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.UserService;
import com.michael.socialmedia.utils.EmailUtils;
import com.michael.socialmedia.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private  final FollowerRepository followerRepository;


    @Override
    public EditUserProfileResponse editUserProfile(EditUserProfileRequest editUserProfileRequest) {
        User loginUser = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(()-> new UserNotAuthenticated("user not authenticated"));
         loginUser.setEmail(editUserProfileRequest.getEmail());
         loginUser.setPassword(editUserProfileRequest.getPassword());
         loginUser.setUsername(editUserProfileRequest.getUsername());
         loginUser.updatedAt();
         userRepository.save(loginUser);
       return   Mapper.mapToEditProfileResponse(loginUser);
    }

    @Override
    public UserProfileResponse viewUserProfile(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("not found"));
        return mapToUserProfile(user);
    }

    @Override
    public List<UserProfileResponse> viewAllUserProfile() {
        List<User>users = userRepository.findAll();
        return users.stream().map(this::mapToUserProfile).collect(Collectors.toList());


    }

    @Override
    public String deleteUserProfile(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("not found"));
           userRepository.delete(user);
           return "user profile deleted successful";
    }

    @Override
    public String toggleFollow(String followerEmail, String followingEmail) {
        User followerUser = userRepository.findByEmail(followerEmail)
                .orElseThrow(()-> new UserNotAuthenticated("user not login inr"));
        User followingUser= userRepository.existsByEmail(followingEmail);
        if(followingUser== null){
            throw new IllegalArgumentException("User to follow not found");

        }
        Follow follower = followerRepository.findByFollowerAndFollowing(followerUser,followingUser);

        if(follower!=null){
            followerRepository.delete(follower);
            return "User unfollowed successfully";
        }
        else {
            Follow newFollow = new Follow();
            newFollow.setFollower(followerUser);
            newFollow.setFollowing(followingUser);
            followerRepository.save(newFollow); // Save the new follow relationship
            return "User followed successfully";
        }

    }




    public UserProfileResponse mapToUserProfile(User user) {
        return  UserProfileResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePic(user.getProfilePic())
                .build();
    }


}
