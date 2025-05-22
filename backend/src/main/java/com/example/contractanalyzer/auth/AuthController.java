package com.example.contractanalyzer.auth;

import com.example.contractanalyzer.model.User;
import com.example.contractanalyzer.security.JwtService;
import com.example.contractanalyzer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService  jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService  = jwtService;
    }

    /* ----------  /auth/register  ---------- */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        User user       = userService.createUser(req);
        String rolesCsv = user.getRoles().stream()
                              .map(Enum::name).collect(Collectors.joining(","));
        String token    = jwtService.generateToken(user.getUsername(), rolesCsv);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /* ----------  /auth/login  ------------- */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        try {
            User user       = userService.authenticate(req.getUsername(), req.getPassword());
            String rolesCsv = user.getRoles().stream()
                                  .map(Enum::name).collect(Collectors.joining(","));
            String token    = jwtService.generateToken(user.getUsername(), rolesCsv);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(403).build();
        }
    }
}
