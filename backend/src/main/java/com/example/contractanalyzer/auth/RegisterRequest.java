package com.example.contractanalyzer.auth;

import lombok.Data;
import java.util.Set;

/**
 * Incoming payload for /auth/register
 */
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Set<String> roles;  // e.g. ["ROLE_USER","ROLE_ADMIN"]
}
