package com.dev.task.movie.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dev.task.movie.model.dto.UserDTO;
import com.dev.task.movie.model.entity.User;
import com.dev.task.movie.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.dev.task.movie.model.entity.Role;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  private final UserService userService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(UserDTO userDTO) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + 3600000); // 1 hour

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    List<String> roles = userDTO.getRoles();

    return JWT.create()
        .withSubject(userDTO.getUsername())
        .withIssuedAt(now)
        .withExpiresAt(validity)
        .withClaim("firstName", userDTO.getFirstName())
        .withClaim("lastName", userDTO.getLastName())
        .withClaim("roles", roles)
        .sign(algorithm);
  }

  public Authentication validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    JWTVerifier verifier = JWT.require(algorithm)
        .build();

    DecodedJWT decoded = verifier.verify(token);

    UserDTO user = UserDTO.builder()
        .username(decoded.getSubject())
        .firstName(decoded.getClaim("firstName").asString())
        .lastName(decoded.getClaim("lastName").asString())
        .build();

    List<String> roles = decoded.getClaim("roles").asList(String.class);

    List<SimpleGrantedAuthority> authorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(user, null, authorities);
  }

  public Authentication validateTokenStrongly(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    JWTVerifier verifier = JWT.require(algorithm)
        .build();

    DecodedJWT decoded = verifier.verify(token);

    UserDTO user = userService.findByUsername(decoded.getSubject());

    List<String> roles = decoded.getClaim("roles").asList(String.class);

    List<SimpleGrantedAuthority> authorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(user, null, authorities);
  }
}