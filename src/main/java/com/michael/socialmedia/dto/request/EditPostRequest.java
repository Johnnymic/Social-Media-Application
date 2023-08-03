package com.michael.socialmedia.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EditPostRequest {
    private String content;
}
