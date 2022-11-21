package com.interpark.triple.domain.travel.acceptance;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.CityRepository;
import com.interpark.triple.domain.travel.domain.entity.Travel;
import com.interpark.triple.domain.travel.domain.repository.TravelRepository;
import com.interpark.triple.domain.travel.dto.TravelCreateRequest;
import com.interpark.triple.domain.travel.dto.TravelUpdateRequest;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.global.acceptance.BaseAcceptanceTest;
import com.interpark.triple.global.response.ResultResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.interpark.triple.domain.travel.acceptance.step.TravelAcceptanceStep.*;
import static com.interpark.triple.domain.users.UserFixtures.USER_GIHYUN_CREATE_ENTITY;
import static com.interpark.triple.global.acceptance.step.AcceptanceStep.assertThatStatusIsOk;
import static com.interpark.triple.global.response.ResultCode.DELETE_TRAVEL_SUCCESS;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Travel 인수/통합 테스트")
class TravelAcceptanceTest extends BaseAcceptanceTest {

  @Autowired UsersRepository usersRepository;
  @Autowired CityRepository cityRepository;
  @Autowired TravelRepository travelRepository;

  @DisplayName("Travel 를 생성한다.")
  @Test
  void createCityTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(USER_GIHYUN_CREATE_ENTITY);
    City givenCity = createSuwonCityEntity(givenUsers);
    TravelCreateRequest travelSuwonCreateRequest =
        TravelCreateRequest.builder()
            .userId(givenUsers.getId())
            .cityId(givenCity.getId())
            .travelStartAt(currentTime.plusDays(1))
            .travelEndAt(currentTime.plusDays(2))
            .build();

    // when
    ExtractableResponse<Response> response = requestToCreateTravel(travelSuwonCreateRequest);

    // then
    assertThatStatusIsOk(response);
    assertThatTravelInfo(response, currentTime);
  }

  @DisplayName("Travel 를 단일 조회 한다.")
  @Test
  void findOneCityTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(USER_GIHYUN_CREATE_ENTITY);
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
    ExtractableResponse<Response> response = requestToFindOneTravel(givenTravel.getId());

    // then
    assertThatStatusIsOk(response);
    assertThatTravelInfo(response, currentTime);
  }

  @DisplayName("Travel 를 수정 한다.")
  @Test
  void updateTravelTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(USER_GIHYUN_CREATE_ENTITY);
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
    ExtractableResponse<Response> response = requestToUpdateTravel(travelSuwonUpdateRequest);

    // then
    assertThatStatusIsOk(response);
    assertThatUpdateTravelInfo(response, currentTime);
  }

  @DisplayName("Travel 를 삭제 한다.")
  @Test
  void deleteTravelTest() {
    // given
    LocalDateTime currentTime = now();
    Users givenUsers = usersRepository.save(USER_GIHYUN_CREATE_ENTITY);
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
    ExtractableResponse<Response> response = requestToDeleteTravel(givenTravel.getId());

    // then
    assertThatStatusIsOk(response);
    assertThatDeleteTravelById(response);
  }

  private City createSuwonCityEntity(Users users) {
    City newSuwonCity = City.builder().name("수원").introContent("간단한 수원 소개").users(users).build();
    return cityRepository.save(newSuwonCity);
  }

  public static void assertThatDeleteTravelById(ExtractableResponse<Response> response) {
    ResultResponse actualResponse = response.body().jsonPath().getObject(".", ResultResponse.class);
    assertAll(
        () -> assertEquals(DELETE_TRAVEL_SUCCESS.getCode(), actualResponse.getCode()),
        () -> assertEquals(DELETE_TRAVEL_SUCCESS.getMessage(), actualResponse.getMessage()),
        () -> assertEquals("", actualResponse.getData()));
  }
}
