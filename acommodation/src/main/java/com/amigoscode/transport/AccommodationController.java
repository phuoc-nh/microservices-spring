package com.amigoscode.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/accommodation")
@Slf4j
public record AccommodationController(AccommodationService accommodationService) {

    @GetMapping
    public List<AccommodationResponse> getAccommodation() {
        return accommodationService.getAccommodation();
    }

//    @GetMapping
//    public void hello() {
//        log.info("Hello from AccommodationController");
//        return;
//    }

    @PostMapping
    public void bookTour(@RequestBody ReservationRequest reserveRequest) {
        accommodationService.reserve(reserveRequest);
        return;
    }

}


