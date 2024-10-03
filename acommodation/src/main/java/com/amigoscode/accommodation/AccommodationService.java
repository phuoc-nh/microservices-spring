package com.amigoscode.accommodation;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccommodationService {
    private final ReservationRepository reservationRepository;
    private final RabbitMQMessageProducer producer;
    private final AccommodationRepository accommodationRepository;

@Transactional
public Reservation reserveAccommodation(Long accommodationId, String customerName, String customerEmail,
                                        Integer numberOfGuests, String checkInDate, String checkOutDate) {
    Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(accommodationId);

    if (optionalAccommodation.isPresent()) {
        Accommodation accommodation = optionalAccommodation.get();

        if (accommodation.getAvailableRooms() > 0) {
            // Update available rooms
            accommodation.setAvailableRooms(accommodation.getAvailableRooms() - 1);
            accommodationRepository.save(accommodation);

            // Calculate total price
            long numberOfNights = ChronoUnit.DAYS.between(LocalDate .parse(checkInDate), LocalDate.parse(checkOutDate));
            BigDecimal totalPrice = accommodation.getPricePerNight().multiply(BigDecimal.valueOf(numberOfNights));

            // Create a new reservation
            Reservation reservation = new Reservation();
            reservation.setAccommodation(accommodation);
            reservation.setCustomerName(customerName);
            reservation.setCustomerEmail(customerEmail);
            reservation.setCheckInDate(LocalDate.parse(checkInDate));
            reservation.setCheckOutDate(LocalDate.parse(checkOutDate));
            reservation.setNumberOfGuests(numberOfGuests);
            reservation.setTotalPrice(totalPrice);
            reservation.setStatus("confirmed");

            var notification = NotificationRequest.builder()
                    .customerId(1)
                    .message("Your accommodation has been reserved successfully.")
                    .sender("Accommodation service")
                    .toCustomerEmail(customerEmail)
                    .build();
            producer.publish(
                    notification,
                    "internal.exchange",
                    "internal.notification.routing-key"
            );

            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("No rooms available for the requested accommodation.");
        }
    } else {
        throw new RuntimeException("Accommodation not found.");
    }
}

}
