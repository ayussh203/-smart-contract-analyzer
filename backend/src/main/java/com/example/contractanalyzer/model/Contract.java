package com.example.contractanalyzer.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Data
public class Contract {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob                 // large text
    private String body;

    private Instant createdAt = Instant.now();
}
