package com.michael.socialmedia.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EditPostRequest {
    private String content;
}
