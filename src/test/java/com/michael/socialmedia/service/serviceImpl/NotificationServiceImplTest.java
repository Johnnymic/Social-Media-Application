package com.michael.socialmedia.service.serviceImpl;

import com.michael.socialmedia.controller.NotificationController;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.NotificationRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.NotificationResponse;
import com.michael.socialmedia.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {


        @InjectMocks
        private NotificationController notificationController;

        @Mock
        private NotificationService notificationService;

    @Test
    public void testSendNotification() {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setSenderId(Long.valueOf("1"));
        notificationRequest.setMessage("Hello dear");
        ApiResponse<String> expectedResponse = new ApiResponse<>("Notification sent successfully");

        ResponseEntity<ApiResponse<String>> responseEntity = notificationController.createNotification(notificationRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(notificationService).createNotification(notificationRequest);
    }


        @Test
        public void testGetUnreadNotifications() {
            User currentUser = new User();
            currentUser.setId(Long.valueOf("1"));
            currentUser.setUsername("michael john");
            List<NotificationResponse> unreadNotifications = new ArrayList<>();
            when(notificationService.getUnreadNotifications(currentUser.getId())).thenReturn(unreadNotifications);

            ResponseEntity<ApiResponse<List<NotificationResponse>>> responseEntity = notificationController.getUnreadNotifications(currentUser.getId());

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(unreadNotifications, responseEntity.getBody().getData());
            verify(notificationService).getUnreadNotifications(currentUser.getId());
        }

        @Test
        public void testMarkNotificationAsRead() {
            Long notificationId = 123L;
            ApiResponse<String> expectedResponse = new ApiResponse<>("Notification marked as read");

            ResponseEntity<ApiResponse<String>> responseEntity = notificationController.markNotificationAsRead(notificationId);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(expectedResponse, responseEntity.getBody());
            verify(notificationService).markNotificationAsRead(notificationId);
        }
    }


