package com.michael.socialmedia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EditCommentRequest {

    private  String content;
}
