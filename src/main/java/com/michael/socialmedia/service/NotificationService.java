package com.michael.socialmedia.service;

import com.michael.socialmedia.dto.request.NotificationRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    String createNotification(NotificationRequest request);

    List<NotificationResponse> getUnreadNotifications(Long receiverId);

    String markNotificationAsRead(Long notificationId);
}
