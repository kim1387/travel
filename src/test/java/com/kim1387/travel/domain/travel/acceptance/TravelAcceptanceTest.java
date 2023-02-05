package com.kim1387.travel.domain.travel.acceptance;

import com.kim1387.travel.domain.city.domain.entity.City;
import com.kim1387.travel.domain.city.domain.repository.CityRepository;
import com.kim1387.travel.domain.travel.domain.entity.Travel;
import com.kim1387.travel.domain.travel.domain.repository.TravelRepository;
import com.kim1387.travel.domain.travel.dto.TravelCreateRequest;
import com.kim1387.travel.domain.travel.dto.TravelUpdateRequest;
import com.kim1387.travel.domain.user.domain.entity.Users;
import com.kim1387.travel.domain.user.domain.repository.UsersRepository;
import com.kim1387.travel.global.acceptance.BaseAcceptanceTest;
import com.kim1387.travel.global.response.ResultResponse;
import com.kim1387.travel.domain.travel.acceptance.step.TravelAcceptanceStep;
import com.kim1387.travel.domain.users.UserFixtures;
import com.kim1387.travel.global.acceptance.step.AcceptanceStep;
import com.kim1387.travel.global.response.ResultCode;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Travel 인수/통합 테스트")
class TravelAcceptanceTest extends BaseAcceptanceTest {

  @Autowired
  UsersRepository usersRepository;
  @Autowired
  CityRepository cityRepository;
  @Autowired
  TravelRepository travelRepository;

  @DisplayName("Travel 를 생성한다.")
  @Test
  void createCityTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(UserFixtures.USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);
    TravelCreateRequest travelSuwonCreateRequest =
        TravelCreateRequest.builder()
            .userId(givenUsers.getId())
            .cityId(givenCity.getId())
            .travelStartAt(currentTime.plusDays(1))
            .travelEndAt(currentTime.plusDays(2))
            .build();

    // when
    ExtractableResponse<Response> response = TravelAcceptanceStep.requestToCreateTravel(travelSuwonCreateRequest);

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    TravelAcceptanceStep.assertThatTravelInfo(response, currentTime);
  }

  @DisplayName("Travel 를 단일 조회 한다.")
  @Test
  void findOneCityTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(UserFixtures.USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);
    Travel givenTravel =
        travelRepository.save(
            Travel.builder()
                .city(givenCity)
                .users(givenUsers)
                .startAt(currentTime.plusDays(1))
                .endAt(currentTime.plusDays(2))
                .build());

    // when
    ExtractableResponse<Response> response = TravelAcceptanceStep.requestToFindOneTravel(givenTravel.getId());

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    TravelAcceptanceStep.assertThatTravelInfo(response, currentTime);
  }

  @DisplayName("Travel 를 수정 한다.")
  @Test
  void updateTravelTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(UserFixtures.USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);

    Travel givenTravel =
        travelRepository.save(
            Travel.builder()
                .city(givenCity)
                .users(givenUsers)
                .startAt(currentTime.plusDays(1))
                .endAt(currentTime.plusDays(2))
                .build());
    TravelUpdateRequest travelSuwonUpdateRequest =
        TravelUpdateRequest.builder()
            .userId(givenUsers.getId())
            .cityId(givenCity.getId())
            .travelId(givenTravel.getId())
            .travelStartAt(currentTime.plusDays(1))
            .travelEndAt(currentTime.plusDays(3))
            .build();
    // when
    ExtractableResponse<Response> response = TravelAcceptanceStep.requestToUpdateTravel(travelSuwonUpdateRequest);

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    TravelAcceptanceStep.assertThatUpdateTravelInfo(response, currentTime);
  }

  @DisplayName("Travel 를 삭제 한다.")
  @Test
  void deleteTravelTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(UserFixtures.USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);

    Travel givenTravel =
        travelRepository.save(
            Travel.builder()
                .city(givenCity)
                .users(givenUsers)
                .startAt(currentTime.plusDays(1))
                .endAt(currentTime.plusDays(2))
                .build());

    // when
    ExtractableResponse<Response> response = TravelAcceptanceStep.requestToDeleteTravel(givenTravel.getId());

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    assertThatDeleteTravelById(response);
  }

  private City createSuwonCityEntity(Users users) {
    City newSuwonCity = City.builder().name("수원").introContent("간단한 수원 소개").users(users).build();
    return cityRepository.save(newSuwonCity);
  }

  public static void assertThatDeleteTravelById(ExtractableResponse<Response> response) {
    ResultResponse actualResponse = response.body().jsonPath().getObject(".", ResultResponse.class);
    assertAll(
        () -> Assertions.assertEquals(ResultCode.DELETE_TRAVEL_SUCCESS.getCode(), actualResponse.getCode()),
        () -> Assertions.assertEquals(ResultCode.DELETE_TRAVEL_SUCCESS.getMessage(), actualResponse.getMessage()),
        () -> assertEquals("", actualResponse.getData()));
  }
}
