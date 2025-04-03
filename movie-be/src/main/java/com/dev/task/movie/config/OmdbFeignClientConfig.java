package com.dev.task.movie.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;

@Configuration
public class OmdbFeignClientConfig {

  @Value("${omdb.api.key}")
  private String apiKey;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> requestTemplate.query("apikey", apiKey);
  }

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL; // Logs headers, body, and metadata
  }
}
