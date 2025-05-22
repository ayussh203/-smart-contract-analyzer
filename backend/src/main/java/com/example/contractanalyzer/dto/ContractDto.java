package com.example.contractanalyzer.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class ContractDto {

    private Long id;
    private String title;
    private String body;
    private Instant createdAt;
}
