package com.kim1387.travel.domain.users.acceptance.step;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kim1387.travel.domain.user.domain.entity.UsersRole;
import com.kim1387.travel.domain.user.dto.UserCreateRequest;
import com.kim1387.travel.domain.user.dto.UserInfo;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

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

  public static void assertThatUserInfo(ExtractableResponse<Response> response) {
    UserInfo expectedUserResponse =
        UserInfo.builder().name("김기현").role(UsersRole.ROLE_USER).build();
    UserInfo actualResponse = response.body().jsonPath().getObject("data", UserInfo.class);
    assertAll(() -> assertEquals(expectedUserResponse, actualResponse));
  }
}
