package com.example.contractanalyzer.auth;

import lombok.Data;

/**
 * Incoming payload for /auth/login
 */
@Data
public class AuthRequest {
    private String username;
    private String password;
}
