package com.amigoscode.transport.notification.rabbitmq;

import com.amigoscode.transport.clients.notification.NotificationRequest;
import com.amigoscode.transport.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationService notificationService;
    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationRequest notificationRequest) {
        log.info("Consumer from RabbitMQ: {}", notificationRequest);
        notificationService.sendNotification(notificationRequest);
    }
}
