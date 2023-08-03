package com.michael.socialmedia.utils;

import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Mapper {



    public static RegisterResponse mapToResponse(User newUser) {
        return RegisterResponse.builder()
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .build();
    }

    public static  EditUserProfileResponse mapToEditProfileResponse(User loginUser) {
        return EditUserProfileResponse.builder()
                .username(loginUser.getUsername())
                .email(loginUser.getEmail())
                .profilePic(loginUser.getProfilePic())
                .build();
    }
    public   static CommentResponse mapToCommentResponse(Comment newComment, Post post) {
        return  CommentResponse.builder()
                .comment(newComment.getContent())
                .post(post)
                .build();
    }

    public static Comment mapToComment(Post post, CommentResponse commentResponse) {
        return Comment.builder()
                .content(commentResponse.getComment())
                .post(post)
                .build();
    }

    public static EditPostResponse mapToEditPostResponse(Post post) {
        return EditPostResponse.builder()
                .content(post.getContent())
                .build();
    }





}
