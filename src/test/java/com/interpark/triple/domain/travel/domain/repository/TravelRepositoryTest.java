package com.interpark.triple.domain.travel.domain.repository;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.CityRepository;
import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.travel.domain.entity.Travel;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.entity.UsersRole;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.global.config.JpaAuditingConfig;
import com.interpark.triple.global.config.QuerydslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({QuerydslConfig.class, JpaAuditingConfig.class})
class TravelRepositoryTest {

  @Autowired private TravelRepository travelRepository;
  @Autowired private UsersRepository usersRepository;
  @Autowired private CityRepository cityRepository;

  private LocalDateTime currentDateTime;

  @BeforeEach
  void setUp() {
    currentDateTime = LocalDateTime.now();
  }

  @Test
  @DisplayName("travel Id로 travel 조회하기 (user 와 city Fetch join)")
  void findTravelWithCityAndUsersByIdTest() {
    // given
    List<Users> givenUserList =
        List.of(
            Users.builder().name("김기현").role(UsersRole.ROLE_USER).build(),
            Users.builder().name("최범균").role(UsersRole.ROLE_USER).build());
    usersRepository.saveAll(givenUserList);

    cityRepository.saveAll(
        List.of(
            City.builder().name("서울").introContent("여기는 서울!").build(),
            City.builder().name("수원").introContent("여기는 수원!").build()));

    travelRepository.saveAll(List.of(Travel.builder().users(givenUserList.get(0)).build()));

    // when

    // then
  }

  @Test
  @DisplayName("travel Id로 travel 조회하기")
  void findTravelByIdTest() {
    // given

    // when

    // then
  }

  @Test
  @DisplayName("userId로 현재 여행중인 도시 여행시작일이 빠른 순으로 정렬하여 List<CityInfo> 조회하기")
  void findCityCurrentTravelOrderByStartAtByUserIdTest() {
    // given
    List<Users> givenUserList = createGivenUserList();

    List<City> givenCityList = createGivenCityList();

    List<Travel> givenTravelList = createGivenTravelList(givenUserList, givenCityList);

    List<City> expectedCityInfoList =
        List.of(
            City.builder()
                .name(givenCityList.get(0).getName())
                .introContent(givenCityList.get(0).getIntroContent())
                .build(),
            City.builder()
                .name(givenCityList.get(2).getName())
                .introContent(givenCityList.get(2).getIntroContent())
                .build());
    // when
    List<City> actualCityList =
        travelRepository.findCurrentTravelOrderByStartAtByUserId(2L).stream()
            .map(Travel::getCity)
            .collect(Collectors.toList());
    // then
    // 서 -> 부 ->  수
    assertAll(
        () -> assertEquals(3, actualCityList.size()),
        () -> assertEquals(expectedCityInfoList.get(0).getName(), actualCityList.get(0).getName()),
        () ->
            assertEquals(
                expectedCityInfoList.get(0).getIntroContent(),
                actualCityList.get(0).getIntroContent()),
        () -> assertEquals(expectedCityInfoList.get(1).getName(), actualCityList.get(1).getName()),
        () ->
            assertEquals(
                expectedCityInfoList.get(1).getIntroContent(),
                actualCityList.get(1).getIntroContent()));
  }

  @Test
  @DisplayName("여행 시작일이 가까운 것부터 여행이 예정된 도시 조회")
  void findWillTravelOrderByStartAtDescTest() {
    // given
    List<Users> givenUserList = createGivenUserList();

    List<City> givenCityList = createGivenCityList();

    List<Travel> givenTravelList = createGivenTravelList(givenUserList, givenCityList);

    List<City> expectedCityInfoList =
        List.of(
            City.builder()
                .name(givenCityList.get(1).getName())
                .introContent(givenCityList.get(1).getIntroContent())
                .build(),
            City.builder()
                .name(givenCityList.get(0).getName())
                .introContent(givenCityList.get(0).getIntroContent())
                .build(),
            City.builder()
                .name(givenCityList.get(2).getName())
                .introContent(givenCityList.get(2).getIntroContent())
                .build());
    // when
    List<CityInfo> actualCityInfoList = travelRepository.findWillTravelOrderByStartAtAsc(1L, 10);

    // then

    // 김기현 수원
    // 김기현 서울
    // 김기현 부산

    assertAll(
        () -> assertEquals(3, actualCityInfoList.size()),
        () ->
            assertEquals(
                expectedCityInfoList.get(0).getName(), actualCityInfoList.get(0).getName()),
        () ->
            assertEquals(
                expectedCityInfoList.get(0).getIntroContent(),
                actualCityInfoList.get(0).getIntroContent()),
        () ->
            assertEquals(
                expectedCityInfoList.get(1).getName(), actualCityInfoList.get(1).getName()),
        () ->
            assertEquals(
                expectedCityInfoList.get(1).getIntroContent(),
                actualCityInfoList.get(1).getIntroContent()),
        () ->
            assertEquals(
                expectedCityInfoList.get(2).getName(), actualCityInfoList.get(2).getName()),
        () ->
            assertEquals(
                expectedCityInfoList.get(2).getIntroContent(),
                actualCityInfoList.get(2).getIntroContent()));
  }

  private List<Travel> createGivenTravelList(List<Users> givenUserList, List<City> givenCityList) {
    return travelRepository.saveAll(
        List.of(
            createTravel(givenUserList.get(0), givenCityList.get(0), -2, 3),
            createTravel(givenUserList.get(0), givenCityList.get(1), -1, 2),
            createTravel(givenUserList.get(0), givenCityList.get(2), -3, 4),
            createTravel(givenUserList.get(1), givenCityList.get(0), 3, 4),
            createTravel(givenUserList.get(1), givenCityList.get(1), 1, 4),
            createTravel(givenUserList.get(1), givenCityList.get(2), 2, 4),
            createTravel(givenUserList.get(0), givenCityList.get(2), 1, 1)));
  }

  private List<City> createGivenCityList() {
    return cityRepository.saveAll(
        List.of(
            City.builder().name("서울").introContent("여기는 서울!").build(),
            City.builder().name("수원").introContent("여기는 수원!").build(),
            City.builder().name("부산").introContent("여기는 부산!").build()));
  }

  private List<Users> createGivenUserList() {
    return usersRepository.saveAll(
        List.of(
            Users.builder().name("김기현").role(UsersRole.ROLE_USER).build(),
            Users.builder().name("최범균").role(UsersRole.ROLE_USER).build()));
  }

  private Travel createTravel(Users user, City city, long startDayMinusDays, long endDayPlusDays) {
    return Travel.builder()
        .users(user)
        .city(city)
        .startAt(currentDateTime.minusDays(startDayMinusDays))
        .endAt(currentDateTime.plusDays(endDayPlusDays))
        .build();
  }
}
