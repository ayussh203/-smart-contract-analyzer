package com.example.contractanalyzer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          // 1) Disable CSRF
          .csrf(AbstractHttpConfigurer::disable)

          // 2) Allow frames (H2 console)
          .headers(headers -> headers.frameOptions().disable())

          // 3) Authorize requests explicitly via AntPathRequestMatcher
          .authorizeHttpRequests(auth -> {
              auth
                .requestMatchers(new AntPathRequestMatcher("/auth/register", "POST"))
                  .permitAll();
              auth
                .requestMatchers(new AntPathRequestMatcher("/auth/login",    "POST"))
                  .permitAll();
              auth
                .requestMatchers(new AntPathRequestMatcher("/health",        "GET"),
                                 new AntPathRequestMatcher("/h2-console/**", "GET"))
                  .permitAll();
              auth
                .anyRequest()
                  .authenticated();
          })

          // 4) Stateless session
          .sessionManagement(sm -> sm.disable())

          // 5) JWT filter
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}
