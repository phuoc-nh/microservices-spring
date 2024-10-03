package com.amigoscode.transport;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TransportResponse {
    private final String name;
    private final String description;
    private final String amount;
    private final Number id;
    private final Number price;
}

