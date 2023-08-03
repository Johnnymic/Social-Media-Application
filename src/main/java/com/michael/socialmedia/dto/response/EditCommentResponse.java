package com.michael.socialmedia.dto.response;

import com.michael.socialmedia.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class EditCommentResponse {

    private Comment comment;
}
