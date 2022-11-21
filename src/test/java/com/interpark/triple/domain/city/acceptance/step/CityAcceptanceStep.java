package com.interpark.triple.domain.city.acceptance.step;

import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import com.interpark.triple.domain.city.dto.CityUpdateRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityAcceptanceStep {

  public static ExtractableResponse<Response> requestToCreateCity(
      CityRegisterRequest cityRegisterRequest) {
    return given()
        .log()
        .all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(cityRegisterRequest)
        .when()
        .post("/api/v1/city")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToFindOneCity(Long cityId) {
    return given()
        .log()
        .all()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("id", cityId)
        .when()
        .get("/api/v1/city/{id}")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToUpdateCity(
      CityUpdateRequest cityUpdateRequest) {
    return given()
        .log()
        .all()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(cityUpdateRequest)
        .when()
        .put("/api/v1/city")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToDeleteCity(Long cityId) {
    return given()
        .log()
        .all()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .pathParam("id", cityId)
        .when()
        .delete("/api/v1/city/{id}")
        .then()
        .log()
        .all()
        .extract();
  }

  public static void assertThatCityInfo(ExtractableResponse<Response> response) {
    CityInfo expectedCityResponse = CityInfo.builder().name("수원").introContent("간단한 수원 소개").build();
    CityInfo actualResponse = response.body().jsonPath().getObject("data", CityInfo.class);
    System.out.println(actualResponse);
    assertAll(() -> assertEquals(expectedCityResponse, actualResponse));
  }

  public static void assertThatUpdatedCityInfo(ExtractableResponse<Response> response) {
    CityInfo expectedCityResponse =
        CityInfo.builder().name("수원").introContent("간단한 수원 소개 수정").build();
    CityInfo actualResponse = response.body().jsonPath().getObject("data", CityInfo.class);
    System.out.println(actualResponse);
    assertAll(() -> assertEquals(expectedCityResponse, actualResponse));
  }
}
