package com.foodwaste.foodwaste.config;

import com.foodwaste.foodwaste.security.CustomUserDetailsService;
import com.foodwaste.foodwaste.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/api/donors/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/donors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/donors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/donors/**").hasRole("ADMIN")
                
                .requestMatchers(HttpMethod.GET, "/api/waste-items/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/waste-items/**").hasAnyRole("ADMIN", "DONOR", "OPERATOR")
                .requestMatchers(HttpMethod.PUT, "/api/waste-items/**").hasAnyRole("ADMIN", "DONOR", "OPERATOR")
                .requestMatchers(HttpMethod.PATCH, "/api/waste-items/**").hasAnyRole("ADMIN", "OPERATOR", "PROCESSOR")
                .requestMatchers(HttpMethod.DELETE, "/api/waste-items/**").hasRole("ADMIN")
                
                .requestMatchers(HttpMethod.GET, "/api/centers/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/centers/**").hasAnyRole("ADMIN", "OPERATOR")
                .requestMatchers(HttpMethod.PUT, "/api/centers/**").hasAnyRole("ADMIN", "OPERATOR")
                .requestMatchers(HttpMethod.DELETE, "/api/centers/**").hasRole("ADMIN")
                
                .requestMatchers(HttpMethod.GET, "/api/processors/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/processors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/processors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/processors/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
