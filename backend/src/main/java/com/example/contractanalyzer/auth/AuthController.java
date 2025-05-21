package com.example.contractanalyzer.auth;

import com.example.contractanalyzer.model.User;
import com.example.contractanalyzer.security.JwtService;
import com.example.contractanalyzer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Register a new user.
     * Returns a JWT on success.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        User user = userService.createUser(req);
        String rolesCsv = String.join(",", 
            user.getRoles().stream().map(Enum::name).toList()
        );
        String token = jwtService.generateToken(user.getUsername(), rolesCsv);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Login with existing credentials.
     * Returns a JWT on success.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        User user = userService.authenticate(req.getUsername(), req.getPassword());
        String rolesCsv = String.join(",", 
            user.getRoles().stream().map(Enum::name).toList()
        );
        String token = jwtService.generateToken(user.getUsername(), rolesCsv);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
