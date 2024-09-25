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
public class Transport {
    @Id
    @SequenceGenerator(
            name = "transport_id_sequence",
            sequenceName = "transport_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transport_id_sequence"
    )
    private Integer id;
    private String transportName;
    private String description;
    private String amount;
    private Integer price;
}
