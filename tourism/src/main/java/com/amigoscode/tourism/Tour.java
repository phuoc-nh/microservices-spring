package com.amigoscode.tourism;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor //
public class Tour {
    @Id
    @SequenceGenerator(
            name = "tour_id_sequence",
            sequenceName = "tour_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tour_id_sequence"
    )
    private Integer id;
    private String tourName;
    private String description;
    private String amount;
}
