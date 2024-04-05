package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // check if fraudster
//        customerRepository.save(customer); // this way we can not get the id of the customer
        customerRepository.saveAndFlush(customer); // this way we can get the id of the customer


        var fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Customer is a fraudster");
        }
        notificationClient.sendNotification(
                NotificationRequest.builder()
                        .customerId(customer.getId())
                        .message("Welcome to our platform")
                        .sender("Amigoscode")
                        .toCustomerEmail(customer.getEmail())
                        .build()
        );
        // send notification
    }


}
