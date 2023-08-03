package com.michael.socialmedia.utils;

import com.michael.socialmedia.domain.Comment;
import com.michael.socialmedia.domain.Post;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.CommentRequest;
import com.michael.socialmedia.dto.request.RegisterRequest;
import com.michael.socialmedia.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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
    public   static CommentResponse mapToCommentResponse(Comment newComment) {
        return  CommentResponse.builder()
                .comment(newComment.getContent())
                .post(Post.builder()
                        .content(newComment.getContent())
                        .id(newComment.getId())
                        .comments(List.of())
                        .build())
                .build();
    }

    public static Comment mapToComment(Post post, CommentRequest commentRequest) {
        return Comment.builder()
                .content(commentRequest.getComment())
                .post(post)
                .build();
    }

    public static EditPostResponse mapToEditPostResponse(Post post) {
        return EditPostResponse.builder()
                .content(post.getContent())
                .build();
    }





}
