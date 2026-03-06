package com.example.User.JWT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration

public class SecurityConfig {


        private JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors().disable();
//        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class );
//       http.authorizeHttpRequests().requestMatchers("/api/users/login",
//                       "/api/users/addUser").permitAll()
//               .anyRequest().authenticated();
//
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 FLIGHT SERVICE APIs (no JWT)
                        .requestMatchers(
                                "/flights/**"
                        ).permitAll()

                        // 🔓 USER SERVICE APIs (no JWT)
                        .requestMatchers(
                                "/api/users/**"
                        ).permitAll()

                        // 🔐 everything else requires JWT
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}