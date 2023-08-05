package com.michael.socialmedia.controller;


import com.michael.socialmedia.dto.request.NotificationRequest;
import com.michael.socialmedia.dto.response.ApiResponse;
import com.michael.socialmedia.dto.response.NotificationResponse;
import com.michael.socialmedia.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping("/create/new/notification")
    public ResponseEntity<ApiResponse<String>> createNotification(@RequestBody NotificationRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>(notificationService.createNotification(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping("/unread/{receiverId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUnreadNotifications(@PathVariable("receiverId") Long receiverId) {
        List<NotificationResponse> notifications = notificationService.getUnreadNotifications(receiverId);
        ApiResponse<List<NotificationResponse>> apiResponse = new ApiResponse<>(notifications);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/mark-read/{notificationId}")
    public ResponseEntity<ApiResponse<String>> markNotificationAsRead(@PathVariable("notificationId") Long notificationId) {
        ApiResponse<String> apiResponse= new ApiResponse<>(notificationService.markNotificationAsRead(notificationId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }






}
