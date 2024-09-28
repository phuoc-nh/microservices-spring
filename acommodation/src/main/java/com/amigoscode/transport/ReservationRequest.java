package com.amigoscode.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {
    private Long accommodationId;
    private String customerName;
    private String customerEmail;
    private Integer numberOfGuests;
    private String checkInDate;
    private String checkOutDate;
}
