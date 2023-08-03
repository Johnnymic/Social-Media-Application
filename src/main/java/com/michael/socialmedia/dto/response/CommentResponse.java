package com.michael.socialmedia.dto.response;

import com.michael.socialmedia.domain.Post;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponse {

    private Long postId;

    private String comment;

    private Post post;


}
