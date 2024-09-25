package com.amigoscode.customer;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.transport.clients.fraud.FraudClient;
import com.amigoscode.transport.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer producer;
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
        var notification = NotificationRequest.builder()
                .customerId(customer.getId())
                .message("Welcome to our platform")
                .sender("Amigoscode")
                .toCustomerEmail(customer.getEmail())
                .build();
        producer.publish(
                notification,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }


}
