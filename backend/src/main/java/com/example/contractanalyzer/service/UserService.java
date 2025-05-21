package com.example.contractanalyzer.service;

import com.example.contractanalyzer.auth.RegisterRequest;
import com.example.contractanalyzer.model.User;
import com.example.contractanalyzer.repository.UserRepository;
import com.example.contractanalyzer.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles user registration and authentication.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;          // JPA repo for persisting User
    private final PasswordEncoder passwordEncoder;  // BCrypt encoder bean
    private final AuthenticationManager authManager;// Spring’s auth manager

    /**
     * Registers a new user with encrypted password and given roles.
     *
     * @param req request containing username, password, and role names
     * @return the saved User entity
     */
    public User createUser(RegisterRequest req) {
        // Convert String roles to enum set
        Set<Role> roles = req.getRoles().stream()
            .map(Role::valueOf)
            .collect(Collectors.toSet());

        // Build user
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(passwordEncoder.encode(req.getPassword()));  // hash pwd
        u.setRoles(roles);

        // Save and return
        return userRepo.save(u);
    }

    /**
     * Authenticates credentials via AuthenticationManager.
     *
     * @param username the username
     * @param password the raw password
     * @return the authenticated User (principal)
     * @throws BadCredentialsException if authentication fails
     */
    public User authenticate(String username, String password) {
        // explicit types instead of var
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(username, password);

        Authentication auth = authManager.authenticate(authToken);
        return (User) auth.getPrincipal();
    }
}
