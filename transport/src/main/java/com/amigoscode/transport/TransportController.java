package com.amigoscode.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/transport")
@Slf4j
public record TransportController(TransportService transportService, TransportRepository transportRepository) {

    @GetMapping("/available")
    public List<Transport> getAllAvailableTransport() {
        return transportRepository.findAll();
    }

    @PostMapping("/book")
    public Booking bookTransport(@RequestBody BookingRequest bookingRequest) {
        return transportService.bookTransport(
                bookingRequest.getTransportId(),
                bookingRequest.getCustomerName(),
                bookingRequest.getCustomerEmail(),
                bookingRequest.getNumberOfPassengers(),
                bookingRequest.getStartTime(),
                bookingRequest.getEndTime()
        );
    }

}


