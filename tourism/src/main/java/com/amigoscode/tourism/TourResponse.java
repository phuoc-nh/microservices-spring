package com.amigoscode.tourism;


import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class TourResponse {
    private final String name;
    private final String description;
    private final String amount;
    private final Number id;
}

