package com.amigoscode.tourism;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tourism")
@Slf4j
public record TourismController(TourismService tourismService, TourRepository tourRepository) {

//    @GetMapping
//    public List<TourResponse> getTours() {
//        return tourismService.getTours();
//    }

//    @PostMapping
//    public void bookTour(@RequestBody BookedTourRequest bookedTour) {
//        tourismService.bookTour(bookedTour);
//        return;
//    }

    @GetMapping("/tours")
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    @PostMapping("/book")
    public TourBooking bookTour(@RequestBody BookedTourRequest bookingRequest) {
        return tourismService.bookTour(
                bookingRequest.getTourId(),
                bookingRequest.getCustomerName(),
                bookingRequest.getCustomerEmail(),
                bookingRequest.getNumberOfPeople()
        );
    }

}


