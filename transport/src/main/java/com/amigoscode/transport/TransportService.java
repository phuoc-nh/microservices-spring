package com.amigoscode.transport;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.transport.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final BookedTransportRepository bookedTransportRepository;
    private final RabbitMQMessageProducer producer;

    public List<TransportResponse> getTransports() {
        var tours = transportRepository.findAll();
        var response = tours.stream()
                .map(t -> TransportResponse.builder()
                        .id(t.getId())
                        .name(t.getTransportName())
                        .description(t.getDescription())
                        .amount(t.getAmount())
                        .price(t.getPrice())
                        .build())
                .toList();

        return response;
    }

    public void bookTour(BookedTransportRequest bookedTourRequest) {
        var bookedTour = BookedTransport.builder()
                .transportId(bookedTourRequest.getTourId())
                .customerEmail(bookedTourRequest.getEmail())
                .build();
        bookedTransportRepository.save(bookedTour);
        var notification = NotificationRequest.builder()
                .customerId(1)
                .message("Confirmation of booking")
                .sender("Tourism service")
                .toCustomerEmail(bookedTourRequest.getEmail())
                .build();
        producer.publish(
                notification,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
