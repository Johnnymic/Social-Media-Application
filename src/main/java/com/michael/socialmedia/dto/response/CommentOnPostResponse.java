package com.michael.socialmedia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentOnPostResponse {
    private Long postId;

    private String comment;
}
