package com.amigoscode.accommodation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.amigoscode.amqp",
                "com.amigoscode.accommodation",
        }
)
public class AccommodationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccommodationApplication.class, args);
    }
}