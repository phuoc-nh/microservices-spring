package com.amigoscode.tourism;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tourism")
@Slf4j
public record TourismController(TourismService tourismService) {

    @GetMapping
    public List<TourResponse> getTours() {
        return tourismService.getTours();
    }

    @PostMapping
    public void bookTour(@RequestBody BookedTourRequest bookedTour) {
        tourismService.bookTour(bookedTour);
        return;
    }

}


