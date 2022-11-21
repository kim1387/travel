package com.interpark.triple.global.acceptance.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceStep {

  public static void assertThatStatusIsOk(ExtractableResponse<Response> response) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }
}
