package com.michael.socialmedia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class CommentOnPostRequest {

    private   Long postId;

    private String commentContent;
}
