package com.amigoscode.tourism;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor //
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tourId;

    private String name;
    private String description;
    private BigDecimal price;
    private String duration;
    private Integer maxParticipants;
    private Integer availableSpots;
    private String location;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TourBooking> bookings;

    // Getters and Setters

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