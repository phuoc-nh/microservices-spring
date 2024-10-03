package com.amigoscode.tourism;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TourismService {
    private final TourRepository tourRepository;
    private final BookedTourRepository tourBookingRepository;
    private final RabbitMQMessageProducer producer;


    @Transactional
    public TourBooking bookTour(Long tourId, String customerName, String customerEmail, Integer numberOfPeople) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);

        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();

            if (tour.getAvailableSpots() >= numberOfPeople) {
                // Update available spots
                tour.setAvailableSpots(tour.getAvailableSpots() - numberOfPeople);
                tourRepository.save(tour);


                TourBooking booking = TourBooking.builder()
                        .tour(tour)
                        .customerName(customerName)
                        .customerEmail(customerEmail)
                        .numberOfPeople(numberOfPeople)
                        .totalPrice(tour.getPrice().multiply(BigDecimal.valueOf(numberOfPeople)))
                        .status("confirmed")
                        .bookingDate(java.time.LocalDateTime.now())
                        .build();


                // handle notifications
                var notification = NotificationRequest.builder()
                        .customerId(1)
                        .message("Your tour has been booked successfully.")
                        .sender("Tourism service")
                        .toCustomerEmail(customerEmail)
                        .build();

                producer.publish(
                        notification,
                        "internal.exchange",
                        "internal.notification.routing-key"
                );

                return tourBookingRepository.save(booking);
            } else {
                throw new RuntimeException("Not enough available spots for the requested tour.");
            }
        } else {
            throw new RuntimeException("Tour not found.");
        }
    }
}


