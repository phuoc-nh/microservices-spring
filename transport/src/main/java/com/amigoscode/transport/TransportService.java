package com.amigoscode.transport;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;
    private final BookedTransportRepository bookingRepository;
    private final RabbitMQMessageProducer producer;

//    public List<TransportResponse> getTransports() {
//        var tours = transportRepository.findAll();
//        var response = tours.stream()
//                .map(t -> TransportResponse.builder()
//                        .id(t.getId())
//                        .name(t.getTransportName())
//                        .description(t.getDescription())
//                        .amount(t.getAmount())
//                        .price(t.getPrice())
//                        .build())
//                .toList();
//
//        return response;
//    }

//    public void bookTour(BookedTransportRequest bookedTourRequest) {
//        var bookedTour = Booking.builder()
//                .transportId(bookedTourRequest.getTourId())
//                .customerEmail(bookedTourRequest.getEmail())
//                .build();
//        bookedTransportRepository.save(bookedTour);
//        var notification = NotificationRequest.builder()
//                .customerId(1)
//                .message("Confirmation of booking")
//                .sender("Tourism service")
//                .toCustomerEmail(bookedTourRequest.getEmail())
//                .build();
//        producer.publish(
//                notification,
//                "internal.exchange",
//                "internal.notification.routing-key"
//        );
//    }

    @Transactional
    public Booking bookTransport(Long transportId, String customerName, String customerEmail,
                                 Integer numberOfPassengers, String startTime, String endTime) {
        Optional<Transport> optionalTransport = transportRepository.findById(transportId);

        if (optionalTransport.isPresent()) {
            Transport transport = optionalTransport.get();

            if (transport.getAvailableUnits() > 0) {
                // Update available units
                transport.setAvailableUnits(transport.getAvailableUnits() - 1);
                transportRepository.save(transport);

                // Calculate total price
                LocalDateTime start = LocalDateTime.parse(startTime);
                LocalDateTime end = LocalDateTime.parse(endTime);
                long hours = Duration.between(start, end).toHours();
                BigDecimal totalPrice = transport.getPricePerHour().multiply(BigDecimal.valueOf(hours));

                // Create a new booking
                Booking booking = new Booking();
                booking.setTransport(transport);
                booking.setCustomerName(customerName);
                booking.setCustomerEmail(customerEmail);
                booking.setStartTime(start);
                booking.setEndTime(end);
                booking.setNumberOfPassengers(numberOfPassengers);
                booking.setTotalPrice(totalPrice);
                booking.setStatus("confirmed");

                return bookingRepository.save(booking);
            } else {
                throw new RuntimeException("No units available for the requested transport.");
            }
        } else {
            throw new RuntimeException("Transport not found.");
        }
    }
}
