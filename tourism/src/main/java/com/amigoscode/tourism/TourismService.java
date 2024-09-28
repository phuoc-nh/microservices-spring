package com.amigoscode.tourism;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.transport.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TourismService {
    private final TourRepository tourRepository;
    private final BookedTourRepository bookedTourRepository;
    private final RabbitMQMessageProducer producer;

    public List<TourResponse> getTours() {
        var tours = tourRepository.findAll();
        var response = tours.stream()
                .map(t -> TourResponse.builder()
                        .id(t.getId())
                        .name(t.getTourName())
                        .description(t.getDescription())
                        .amount(t.getAmount())
                        .build())
                .toList();

        return response;
    }

    public void bookTour(BookedTourRequest bookedTourRequest) {
        var bookedTour = BookedTour.builder()
                .tourId(bookedTourRequest.getTourId())
                .customerEmail(bookedTourRequest.getEmail())
                .build();
        bookedTourRepository.save(bookedTour);
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
