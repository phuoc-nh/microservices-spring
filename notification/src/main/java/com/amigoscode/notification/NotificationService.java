package com.amigoscode.notification;

import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final JavaMailSender mailSender;

    public void sendNotification(NotificationRequest notificationRequest) {
        var notification = Notification.builder()
                .message(notificationRequest.getMessage())
                .sender(notificationRequest.getSender())
                .toCustomerEmail(notificationRequest.getToCustomerEmail())
                .customerId(notificationRequest.getCustomerId())
                .sendAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);

        sendEmail("huuphuoc252513@gmail.com", "Booking success", notification.getMessage());
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
