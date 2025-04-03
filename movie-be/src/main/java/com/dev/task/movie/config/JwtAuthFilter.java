package com.dev.task.movie.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserAuthenticationProvider userAuthenticationProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null) {
      String[] authElements = header.split(" ");

      if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
        try {
          String token = authElements[1];
          var authentication = userAuthenticationProvider.validateToken(token);

          if ("GET".equals(request.getMethod())) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
          } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }

        } catch (RuntimeException e) {
          SecurityContextHolder.clearContext();
          throw e;
        }
      }
    }

    filterChain.doFilter(request, response);
  }
}