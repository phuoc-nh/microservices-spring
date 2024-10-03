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

//    public List<TourResponse> getTours() {
//        var tours = tourRepository.findAll();
//        var response = tours.stream()
//                .map(t -> TourResponse.builder()
//                        .id(t.getTourId())
//                        .name(t.getTourName())
//                        .description(t.getDescription())
//                        .amount(t.getAmount())
//                        .build())
//                .toList();
//
//        return response;
//    }

//    public void bookTour(BookedTourRequest bookedTourRequest) {
//        var bookedTour = BookedTour.builder()
//                .tourId(bookedTourRequest.getTourId())
//                .customerEmail(bookedTourRequest.getEmail())
//                .build();
//        bookedTourRepository.save(bookedTour);
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
    public TourBooking bookTour(Long tourId, String customerName, String customerEmail, Integer numberOfPeople) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);

        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();

            if (tour.getAvailableSpots() >= numberOfPeople) {
                // Update available spots
                tour.setAvailableSpots(tour.getAvailableSpots() - numberOfPeople);
                tourRepository.save(tour);

                // Create a new booking
//                TourBooking booking = new TourBooking();

//                booking.setTour(tour);
//                booking.setCustomerName(customerName);
//                booking.setCustomerEmail(customerEmail);
//                booking.setNumberOfPeople(numberOfPeople);
//                booking.setTotalPrice(tour.getPrice().multiply(BigDecimal.valueOf(numberOfPeople)));
//                booking.setStatus("confirmed");
//                booking.setBookingDate(java.time.LocalDateTime.now());

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
                        .message("Confirmation of booking")
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


