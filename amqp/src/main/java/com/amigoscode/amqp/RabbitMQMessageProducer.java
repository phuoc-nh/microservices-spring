package com.amigoscode.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class RabbitMQMessageProducer {
    private final AmqpTemplate amqpTemplate;

    public  void publish(Object payload, String exchange, String routingKey) {
        log.info("Sending message to exchange: {} with routing key: {}, payload: {}", exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Sent message to exchange: {} with routing key: {}, payload: {}", exchange, routingKey, payload);
    }
}
