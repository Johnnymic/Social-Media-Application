package com.michael.socialmedia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class NotificationRequest {
    private Long senderId;
    private Long receiverId;
    private String message;
    private Long postId;
    private Long commentId;
}
