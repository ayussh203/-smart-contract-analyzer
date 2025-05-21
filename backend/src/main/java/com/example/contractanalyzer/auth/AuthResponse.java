package com.example.contractanalyzer.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Outgoing payload with the issued JWT
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
