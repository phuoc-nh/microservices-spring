package com.amigoscode.transport.notification;

import com.amigoscode.transport.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest notificationRequest) {
        var notification = Notification.builder()
                .message(notificationRequest.getMessage())
                .sender(notificationRequest.getSender())
                .toCustomerEmail(notificationRequest.getToCustomerEmail())
                .customerId(notificationRequest.getCustomerId())
                .sendAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }
}
