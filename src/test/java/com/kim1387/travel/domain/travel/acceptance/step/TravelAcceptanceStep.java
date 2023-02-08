package com.kim1387.travel.domain.travel.acceptance.step;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kim1387.travel.domain.city.dto.CityInfo;
import com.kim1387.travel.domain.travel.dto.TravelCreateRequest;
import com.kim1387.travel.domain.travel.dto.TravelInfo;
import com.kim1387.travel.domain.travel.dto.TravelUpdateRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.springframework.http.MediaType;

public class TravelAcceptanceStep {

  public static ExtractableResponse<Response> requestToCreateTravel(
      TravelCreateRequest travelCreateRequest) {
    return given()
        .log()
        .all()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(travelCreateRequest)
        .when()
        .post("/api/v1/travel")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToFindOneTravel(Long travelId) {
    return given()
        .log()
        .all()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("id", travelId)
        .when()
        .get("/api/v1/travel/{id}")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToUpdateTravel(
      TravelUpdateRequest travelUpdateRequest) {
    return given()
        .log()
        .all()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(travelUpdateRequest)
        .when()
        .put("/api/v1/travel")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToDeleteTravel(Long travelId) {
    return given()
        .log()
        .all()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .pathParam("id", travelId)
        .when()
        .delete("/api/v1/travel/{id}")
        .then()
        .log()
        .all()
        .extract();
  }

  public static void assertThatTravelInfo(
      ExtractableResponse<Response> response, LocalDateTime currentTime) {
    TravelInfo expectedTravelResponse =
        TravelInfo.builder()
            .cityName("수원")
            .userName("김기현")
            .startTravelAt(currentTime.plusDays(1))
            .endTravelAt(currentTime.plusDays(2))
            .build();
    TravelInfo actualResponse = response.body().jsonPath().getObject("data", TravelInfo.class);
    assertAll(() -> assertEquals(expectedTravelResponse, actualResponse));
  }

  public static void assertThatUpdateTravelInfo(
      ExtractableResponse<Response> response, LocalDateTime currentTime) {
    TravelInfo expectedTravelResponse =
        TravelInfo.builder()
            .cityName("수원")
            .userName("김기현")
            .startTravelAt(currentTime.plusDays(1))
            .endTravelAt(currentTime.plusDays(3))
            .build();
    TravelInfo actualResponse = response.body().jsonPath().getObject("data", TravelInfo.class);
    assertAll(() -> assertEquals(expectedTravelResponse, actualResponse));
  }

  public static void assertThatUpdatedCityInfo(ExtractableResponse<Response> response) {
    CityInfo expectedCityResponse =
        CityInfo.builder().name("수원").introContent("간단한 수원 소개 수정").build();
    CityInfo actualResponse = response.body().jsonPath().getObject("data", CityInfo.class);
    System.out.println(actualResponse);
    assertAll(() -> assertEquals(expectedCityResponse, actualResponse));
  }
}
