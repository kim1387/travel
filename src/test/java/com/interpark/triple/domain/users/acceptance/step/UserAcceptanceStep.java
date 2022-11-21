package com.interpark.triple.domain.users.acceptance.step;

import com.interpark.triple.domain.user.dto.UserCreateRequest;
import com.interpark.triple.domain.user.dto.UserInfo;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static com.interpark.triple.domain.user.domain.entity.UsersRole.ROLE_USER;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.LOCATION;

public class UserAcceptanceStep {

  public static ExtractableResponse<Response> requestToCreateUser(
      UserCreateRequest userCreateRequest) {
    return given()
        .log()
        .all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(userCreateRequest)
        .when()
        .post("/api/v1/users")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToFindOneUser(Long userId) {
    return given()
        .log()
        .all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("id", userId)
        .when()
        .get("/api/v1/users/{id}")
        .then()
        .log()
        .all()
        .extract();
  }

  public static ExtractableResponse<Response> requestToFindOneUser(String createdLocation) {
    return given()
        .log()
        .all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get(createdLocation)
        .then()
        .log()
        .all()
        .extract();
  }

  public static String requestToCreateUserAndGetLocation(UserCreateRequest userCreateRequest) {
    return requestToCreateUser(userCreateRequest).header(LOCATION);
  }

  public static void assertThatUserInfo(ExtractableResponse<Response> response) {
    UserInfo expectedUserResponse = UserInfo.builder().name("김기현").role(ROLE_USER).build();
    UserInfo actualResponse = response.body().jsonPath().getObject("data", UserInfo.class);
    System.out.println(actualResponse);
    assertAll(() -> assertEquals(expectedUserResponse, actualResponse));
  }
}
