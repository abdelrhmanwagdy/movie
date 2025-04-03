package com.dev.task.movie.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
  private final UserAuthenticationProvider userAuthenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
        .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
            .requestMatchers(HttpMethod.DELETE, "/api/movies/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/movies/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/movies/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/movies/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/movies/**").hasAnyRole("ADMIN","USER")
            .requestMatchers(HttpMethod.GET, "/api/paged-movies/**").hasAnyRole("ADMIN","USER")
            .requestMatchers(HttpMethod.GET, "/api/omdb/**").hasRole("ADMIN")

            .anyRequest()
            .authenticated());

    return http.build();
  }
}