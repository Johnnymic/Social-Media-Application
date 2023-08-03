package com.michael.socialmedia.dto.request;

import com.michael.socialmedia.domain.Post;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentRequest {
    private Long postId;

    private String comment;

    private Post post;
}
