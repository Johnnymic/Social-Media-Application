package com.michael.socialmedia.service.serviceImpl;

import com.michael.socialmedia.domain.Notifications;
import com.michael.socialmedia.domain.User;
import com.michael.socialmedia.dto.request.NotificationRequest;

import com.michael.socialmedia.dto.response.NotificationResponse;
import com.michael.socialmedia.exceptions.NotificationNotFoundException;
import com.michael.socialmedia.exceptions.ResourceNotFoundException;
import com.michael.socialmedia.repository.NotificationRepository;
import com.michael.socialmedia.repository.UserRepository;
import com.michael.socialmedia.service.NotificationService;
import com.michael.socialmedia.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    @Override
    public String createNotification(NotificationRequest request) {
        User sender = userRepository.findByEmailAndId(EmailUtils.getEmailFromContent(), request.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        User receiver = userRepository.findByEmailAndId(EmailUtils.getEmailFromContent(), request.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        Notifications notifications = mapToNotification(receiver, sender, request);
        notificationRepository.save(notifications);
        return "Notification has been created";
    }

    @Override
    public List<NotificationResponse> getUnreadNotifications(Long receiverId) {
        List<Notifications> unreadNotifications = notificationRepository.findAll();
        return unreadNotifications.stream().map(this::mapToNotificationResponse).collect(Collectors.toList());
    }

    @Override
    public String markNotificationAsRead(Long notificationId) {
        Notifications notifications = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("notification not found exception"));
        notifications.setRead(true);
        notificationRepository.save(notifications);
        return "Notification as be marked" ;
    }


    private Notifications mapToNotification(User receiver, User sender, NotificationRequest request) {
        return Notifications.builder()
                .receiver(receiver)
                .sender(sender)
                .message(request.getMessage())
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();
    }

    private NotificationResponse mapToNotificationResponse(Notifications notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .senderUsername(notification.getSender().getUsername())
                .read(notification.isRead())
                .build();
    }

}
