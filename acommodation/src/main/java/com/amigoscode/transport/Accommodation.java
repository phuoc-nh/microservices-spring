package com.amigoscode.transport;

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
public class Accommodation {
    @Id
    @SequenceGenerator(
            name = "accommodation_id_sequence",
            sequenceName = "accommodation_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "accommodation_id_sequence"
    )
    private Integer id;
    private String accommodationName;
    private String description;
    private Integer amount;
    private Integer price;
}
