package com.amigoscode.transport;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.amigoscode.amqp",
                "com.amigoscode.transport",
        }
)
public class TransportApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransportApplication.class, args);
    }
}