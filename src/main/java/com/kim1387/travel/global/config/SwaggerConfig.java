package com.kim1387.travel.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI bauctionOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("여행지 관리 API").description("여행지 관리 API 입니다.").version("v0.0.1"));
  }
}
