package com.amigoscode.accommodation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/accommodation")
@Slf4j
public record AccommodationController(AccommodationService accommodationService, AccommodationRepository accommodationRepository, ReservationRepository reservationRepository) {

    @GetMapping("/available")
    public List<Accommodation> getAllAvailableAccommodations() {
        return accommodationRepository.findAll();
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    @PostMapping("/reserve")
    public Reservation reserveAccommodation(@RequestBody ReservationRequest reservationRequest) {
        return accommodationService.reserveAccommodation(
                reservationRequest.getAccommodationId(),
                reservationRequest.getCustomerName(),
                reservationRequest.getCustomerEmail(),
                reservationRequest.getNumberOfGuests(),
                reservationRequest.getCheckInDate(),
                reservationRequest.getCheckOutDate()
        );
    }

}


