package com.amigoscode.accommodation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AccommodationResponse {
    private final String name;
    private final String description;
    private final Integer amount;
    private final Integer price;
    private final Number id;
}

