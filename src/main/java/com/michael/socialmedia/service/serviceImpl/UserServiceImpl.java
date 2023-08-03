package com.michael.socialmedia.service.serviceImpl;

import com.cloudinary.utils.ObjectUtils;
import com.michael.socialmedia.Config.CloudinaryConfig;
import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.Follow;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentOnPostRequest;
import com.michael.socialmedia.dto.request.EditUserProfileRequest;
import com.michael.socialmedia.dto.response.CommentOnPostResponse;
import com.michael.socialmedia.dto.response.EditUserProfileResponse;
import com.michael.socialmedia.dto.response.UserProfileResponse;
import com.michael.socialmedia.exceptions.CommentNotFoundException;
import com.michael.socialmedia.exceptions.PostNotfoundException;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.exceptions.UserNotAuthenticated;
import com.michael.socialmedia.repository.*;
import com.michael.socialmedia.service.UserService;
import com.michael.socialmedia.utils.EmailUtils;
import com.michael.socialmedia.utils.Mapper;
import com.michael.socialmedia.utils.UserPage;
import com.michael.socialmedia.utils.UserSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private  final FollowerRepository followerRepository;

    private  final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final UserCriteriaRepository userCriteriaRepository;

    private  final CloudinaryConfig cloudinaryConfig;

    @Override
    public EditUserProfileResponse editUserProfile(EditUserProfileRequest editUserProfileRequest) {
        User loginUser = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(()-> new UserNotAuthenticated("user not authenticated"));
          loginUser.setUsername(editUserProfileRequest.getUsername());
          loginUser.setProfilePic(editUserProfileRequest.getProfilePic());
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

    @Override
    public CommentOnPostResponse commentOnPost(CommentOnPostRequest comment) {
        User loginUser = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(()-> new UserNotAuthenticated("user not authenticated"));
        Post existingPost =postRepository.findById(comment.getPostId())
                .orElseThrow(()-> new PostNotfoundException("post not found"));
        Comment commentPost  = new Comment();
        commentPost.setContent(comment.getCommentContent());
        commentPost.setUser(loginUser);
        existingPost.getComments().add(commentPost);
        postRepository.save(existingPost);
      var newComment=  commentRepository.save(commentPost);
        return CommentOnPostResponse.builder()
                .postId(newComment.getPost().getId())
                .comment(newComment.getContent())
                .build();
    }

    @Override
    public String removeCommentFromPost(Long commentId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotfoundException("Post not found"));
        Comment comment = post.getComments().stream()
                .filter(comment1 -> comment1.getId().equals(commentId))
                .findFirst().orElseThrow(()-> new CommentNotFoundException("comment not found"));
        post.getComments().remove(comment);
        return "comment successfully removed from post";
    }

    @Override
    public Page<User> filterAndSearch(UserPage userPage,UserSearchCriteria userSearchCriteria) {
      return  userCriteriaRepository.findAllUserWithFilter(userPage,userSearchCriteria);

    }

    @Override
    public Object uploadProfilePic(MultipartFile multipartFile) throws IOException {
        User user = userRepository.findByEmail(EmailUtils.getEmailFromContent())
                .orElseThrow(()-> new ResourceNotFoundException("user id not found"));
        String userProfilePid = uploadProfileImage(multipartFile);
        user.setProfilePic(userProfilePid);
        userRepository.save(user);
        return multipartFile;
    }

    private String uploadProfileImage(MultipartFile multipartFile) throws IOException {
         File file= convertToMultipartFile(multipartFile);
         try {
             Map uploadResult = cloudinaryConfig.cloudinary().uploader().upload(file, ObjectUtils.asMap("use_file_name", true, "unique_fileName", true));
             boolean isFileDeleted = file.delete();
             if (isFileDeleted) {
                 log.info("File successfully deleted");
             } else
                 log.info("File doesn't exist");
          return    uploadResult.get("url").toString();
         }catch (IOException e){
             throw  new IOException(e);
         }


    }

    private File convertToMultipartFile(MultipartFile multipartFile) throws IOException {
        String file = multipartFile.getOriginalFilename();
        if(file==null){
            throw  new AssertionError();
        }
        File FileReaderConverter = new File(file);
        FileOutputStream fileOutputStream = new FileOutputStream(FileReaderConverter);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return FileReaderConverter;

    }


    public UserProfileResponse mapToUserProfile(User user) {
        return  UserProfileResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePic(user.getProfilePic())
                .build();
    }


}
