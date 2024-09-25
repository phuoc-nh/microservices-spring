package com.amigoscode.transport;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.transport.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;
    private final RabbitMQMessageProducer producer;

    public List<AccommodationResponse> getAccommodation() {
        var tours = accommodationRepository.findAll();
        var response = tours.stream()
                .map(t -> AccommodationResponse.builder()
                        .id(t.getId())
                        .name(t.getAccommodationName())
                        .description(t.getDescription())
                        .amount(t.getAmount())
                        .price(t.getPrice())
                        .build())
                .toList();

        return response;
    }

    public void reserve(ReservationRequest reservationRequest) {
        var bookedTour = Reservation.builder()
                .accommodationId(reservationRequest.getAccommodationId())
                .customerEmail(reservationRequest.getEmail())
                .build();
        reservationRepository.save(bookedTour);
        var notification = NotificationRequest.builder()
                .customerId(1)
                .message("Confirmation of booking")
                .sender("Accommodation service")
                .toCustomerEmail(reservationRequest.getEmail())
                .build();
        producer.publish(
                notification,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
