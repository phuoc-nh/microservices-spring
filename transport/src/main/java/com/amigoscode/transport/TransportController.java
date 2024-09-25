package com.amigoscode.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/transports")
@Slf4j
public record TransportController(TransportService transportService) {

    @GetMapping
    public List<TransportResponse> getTours() {
        return transportService.getTransports();
    }

    @PostMapping
    public void bookTour(@RequestBody BookedTransportRequest bookedTour) {
        transportService.bookTour(bookedTour);
        return;
    }

}


