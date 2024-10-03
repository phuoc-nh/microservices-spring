package com.amigoscode.tourism;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TourBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    private String customerName;
    private String customerEmail;
    private Integer numberOfPeople;
    private BigDecimal totalPrice;
    private String status; // e.g., "confirmed", "cancelled"

    private LocalDateTime bookingDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Constructors, Getters, and Setters omitted for brevity
}
