package com.amigoscode.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private Long transportId;
        private String customerName;
    private String customerEmail;
    private Integer numberOfPassengers;
    private String startTime;
    private String endTime;
}
