package com.kim1387.travel.domain.city.acceptance;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kim1387.travel.domain.city.acceptance.step.CityAcceptanceStep;
import com.kim1387.travel.domain.city.domain.entity.City;
import com.kim1387.travel.domain.city.domain.repository.CityRepository;
import com.kim1387.travel.domain.city.dto.CityRegisterRequest;
import com.kim1387.travel.domain.city.dto.CityUpdateRequest;
import com.kim1387.travel.domain.user.domain.entity.Users;
import com.kim1387.travel.domain.user.domain.entity.UsersRole;
import com.kim1387.travel.domain.user.domain.repository.UsersRepository;
import com.kim1387.travel.domain.users.UserFixtures;
import com.kim1387.travel.global.acceptance.BaseAcceptanceTest;
import com.kim1387.travel.global.acceptance.step.AcceptanceStep;
import com.kim1387.travel.global.response.ResultCode;
import com.kim1387.travel.global.response.ResultResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("City 인수/통합 테스트")
class CityAcceptanceTest extends BaseAcceptanceTest {

  @Autowired UsersRepository usersRepository;
  @Autowired CityRepository cityRepository;

  @DisplayName("City 를 생성한다.")
  @Test
  void createCityTest() {
    // given
    Users givenUsers =
        usersRepository.save(Users.builder().name("김기현").role(UsersRole.ROLE_USER).build());
    CityRegisterRequest givenRequest =
        CityRegisterRequest.builder()
            .userId(givenUsers.getId())
            .cityName("수원")
            .cityIntroContent("간단한 수원 소개")
            .build();

    // when
    ExtractableResponse<Response> response = CityAcceptanceStep.requestToCreateCity(givenRequest);

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    CityAcceptanceStep.assertThatCityInfo(response);
  }

  @DisplayName("City 를 단일 조회 한다.")
  @Test
  void findOneCityTest() {
    // given
    Users givenUsers = usersRepository.save(UserFixtures.USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);

    // when
    ExtractableResponse<Response> response =
        CityAcceptanceStep.requestToFindOneCity(givenCity.getId());

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    CityAcceptanceStep.assertThatCityInfo(response);
  }

  private City createSuwonCityEntity(Users users) {
    City newSuwonCity = City.builder().name("수원").introContent("간단한 수원 소개").users(users).build();
    return cityRepository.save(newSuwonCity);
  }

  @DisplayName("City 정보를 수정 한다.")
  @Test
  void updateCityTest() {
    // given
    Users givenUsers = usersRepository.save(UserFixtures.USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);
    CityUpdateRequest givenSuwonUpdateRequest =
        CityUpdateRequest.builder()
            .cityId(givenCity.getId())
            .cityName("수원")
            .cityIntroContent("간단한 수원 소개 수정")
            .build();

    // when
    ExtractableResponse<Response> response =
        CityAcceptanceStep.requestToUpdateCity(givenSuwonUpdateRequest);

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    CityAcceptanceStep.assertThatUpdatedCityInfo(response);
  }

  @DisplayName("City 정보를 삭제 한다.")
  @Test
  void deleteCityTest() {
    // given
    Users givenUsers = usersRepository.save(UserFixtures.USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);

    // when
    ExtractableResponse<Response> response =
        CityAcceptanceStep.requestToDeleteCity(givenCity.getId());

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    assertThatDeleteCityById(response);
  }

  public static void assertThatDeleteCityById(ExtractableResponse<Response> response) {
    ResultResponse actualResponse = response.body().jsonPath().getObject(".", ResultResponse.class);
    assertAll(
        () ->
            Assertions.assertEquals(
                ResultCode.DELETE_CITY_SUCCESS.getCode(), actualResponse.getCode()),
        () ->
            Assertions.assertEquals(
                ResultCode.DELETE_CITY_SUCCESS.getMessage(), actualResponse.getMessage()),
        () -> assertEquals("", actualResponse.getData()));
  }
}
