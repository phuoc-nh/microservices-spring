package com.amigoscode.tourism;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookedTourRequest {
//    private String email;
//    private Integer tourId;

    private Long tourId;
    private String customerName;
    private String customerEmail;
    private Integer numberOfPeople;
}
