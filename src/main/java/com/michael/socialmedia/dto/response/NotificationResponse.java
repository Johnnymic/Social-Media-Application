package com.michael.socialmedia.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long id;
    private String senderUsername;
    private String message;
    private String timestamp;
    private boolean read;
}
