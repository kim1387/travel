package com.kim1387.travel.domain.travel.domain.repository;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kim1387.travel.domain.city.domain.entity.City;
import com.kim1387.travel.domain.city.domain.repository.CityRepository;
import com.kim1387.travel.domain.city.dto.CityInfo;
import com.kim1387.travel.domain.travel.domain.entity.Travel;
import com.kim1387.travel.domain.travel.exception.NotFoundTravelEntityException;
import com.kim1387.travel.domain.user.domain.entity.Users;
import com.kim1387.travel.domain.user.domain.entity.UsersRole;
import com.kim1387.travel.domain.user.domain.repository.UsersRepository;
import com.kim1387.travel.global.config.JpaAuditingConfig;
import com.kim1387.travel.global.config.QuerydslConfig;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({QuerydslConfig.class, JpaAuditingConfig.class})
class TravelRepositoryTest {

  @Autowired private TravelRepository travelRepository;
  @Autowired private UsersRepository usersRepository;
  @Autowired private CityRepository cityRepository;

  private LocalDateTime currentDateTime;

  @BeforeEach
  void setUp() {
    currentDateTime = now();
  }

  @Test
  @DisplayName("여행 중인 도시 : 여행 시작일이 빠른 것부터 조회 test")
  void findCurrentTravelOrderByStartAtTest() {
    // given
    Users givenUser = Users.builder().name("김기현").role(UsersRole.ROLE_USER).build();
    usersRepository.save(givenUser);

    List<City> expectedCityList =
        cityRepository.saveAll(
            List.of(
                City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build(),
                City.builder().name("수원").introContent("여기는 수원!").users(givenUser).build(),
                City.builder().name("광주").introContent("여기는 광주!").users(givenUser).build(),
                City.builder().name("부산").introContent("여기는 부산!").users(givenUser).build()));
    currentDateTime = now();
    List<Travel> givenTravelList =
        travelRepository.saveAll(
            List.of(
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(0))
                    .startAt(currentDateTime.minusDays(3))
                    .endAt(currentDateTime.plusDays(1))
                    .build(),
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(1))
                    .startAt(currentDateTime.minusDays(2))
                    .endAt(currentDateTime.plusDays(1))
                    .build(),
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(2))
                    .startAt(currentDateTime.minusDays(1))
                    .endAt(currentDateTime.plusDays(1))
                    .build(),
                // 여행 예정
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(3))
                    .startAt(currentDateTime.minusDays(-1))
                    .endAt(currentDateTime.plusDays(2))
                    .build()));

    List<CityInfo> expectedCurrentTravelOrderByStartAtList =
        Arrays.asList(
            CityInfo.builder()
                .name(expectedCityList.get(2).getName())
                .introContent(expectedCityList.get(2).getIntroContent())
                .build(),
            CityInfo.builder()
                .name(expectedCityList.get(1).getName())
                .introContent(expectedCityList.get(1).getIntroContent())
                .build(),
            CityInfo.builder()
                .name(expectedCityList.get(0).getName())
                .introContent(expectedCityList.get(0).getIntroContent())
                .build());

    // when
    List<CityInfo> actualCurrentTravelOrderByStartAtList =
        travelRepository.findCurrentTravelOrderByStartAt(
            givenTravelList.get(0).getUsers().getId(), 10);
    // then
    assertAll(
        () -> assertEquals(3, actualCurrentTravelOrderByStartAtList.size()),
        () ->
            assertEquals(
                expectedCurrentTravelOrderByStartAtList.get(0),
                actualCurrentTravelOrderByStartAtList.get(0)),
        () ->
            assertEquals(
                expectedCurrentTravelOrderByStartAtList.get(1),
                actualCurrentTravelOrderByStartAtList.get(1)),
        () ->
            assertEquals(
                expectedCurrentTravelOrderByStartAtList.get(2),
                actualCurrentTravelOrderByStartAtList.get(2)));
  }

  @Test
  @DisplayName("travel Id로 travel 조회하기 (user 와 city Fetch join)")
  void findTravelWithCityAndUsersByIdTest() {
    // given
    Users givenUser = Users.builder().name("김기현").role(UsersRole.ROLE_USER).build();
    usersRepository.save(givenUser);

    List<City> expectedCityList =
        cityRepository.saveAll(
            List.of(
                City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build(),
                City.builder().name("수원").introContent("여기는 수원!").users(givenUser).build()));
    currentDateTime = now();
    List<Travel> expectedTravelList =
        travelRepository.saveAll(
            List.of(
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(0))
                    .startAt(currentDateTime.minusDays(1))
                    .endAt(now().plusDays(1))
                    .build(),
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(1))
                    .startAt(currentDateTime.minusDays(1))
                    .endAt(now().plusDays(1))
                    .build()));

    // when
    Travel actualTravelWithCityAndUsersById =
        travelRepository
            .findTravelWithCityAndUsersById(expectedTravelList.get(0).getId())
            .orElseThrow(NotFoundTravelEntityException::new);
    // then
    assertAll(
        () ->
            Assertions.assertEquals(
                expectedCityList.get(0), actualTravelWithCityAndUsersById.getCity()),
        () -> Assertions.assertEquals(givenUser, actualTravelWithCityAndUsersById.getUsers()),
        () ->
            assertEquals(
                currentDateTime.minusDays(1).getSecond(),
                actualTravelWithCityAndUsersById.getStartAt().getSecond()),
        () ->
            assertEquals(
                currentDateTime.plusDays(1).getSecond(),
                actualTravelWithCityAndUsersById.getEndAt().getSecond()));
  }

  @Test
  @DisplayName("travel Id로 travel 조회하기")
  void findTravelByIdTest() {
    // given
    Users givenUser = Users.builder().name("김기현").role(UsersRole.ROLE_USER).build();
    usersRepository.save(givenUser);

    List<City> expectedCityList =
        cityRepository.saveAll(
            List.of(
                City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build(),
                City.builder().name("수원").introContent("여기는 수원!").users(givenUser).build()));
    currentDateTime = now();
    List<Travel> expectedTravelList =
        travelRepository.saveAll(
            List.of(
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(0))
                    .startAt(currentDateTime.minusDays(1))
                    .endAt(now().plusDays(1))
                    .build(),
                Travel.builder()
                    .users(givenUser)
                    .city(expectedCityList.get(1))
                    .startAt(currentDateTime.minusDays(1))
                    .endAt(now().plusDays(1))
                    .build()));

    // when
    Travel actualTravelWithCityAndUsersById =
        travelRepository
            .findTravelWithCityAndUsersById(expectedTravelList.get(0).getId())
            .orElseThrow(NotFoundTravelEntityException::new);
    // then
    assertAll(
        () ->
            Assertions.assertEquals(
                expectedCityList.get(0), actualTravelWithCityAndUsersById.getCity()),
        () -> Assertions.assertEquals(givenUser, actualTravelWithCityAndUsersById.getUsers()),
        () ->
            assertEquals(
                currentDateTime.minusDays(1).getSecond(),
                actualTravelWithCityAndUsersById.getStartAt().getSecond()),
        () ->
            assertEquals(
                currentDateTime.plusDays(1).getSecond(),
                actualTravelWithCityAndUsersById.getEndAt().getSecond()));
  }

  @Test
  @DisplayName("userId로 현재 여행중인 도시 여행시작일이 빠른 순으로 정렬하여 Travel List 조회하기")
  void findCityCurrentTravelOrderByStartAtByUserIdTest() {
    // given
    List<Users> givenUserList = createGivenUserList();

    List<City> givenCityList = createGivenCityList(givenUserList.get(0));

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
        travelRepository
            .findCurrentTravelOrderByStartAtByUserId(givenUserList.get(1).getId())
            .stream()
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

    List<City> givenCityList = createGivenCityList(givenUserList.get(0));

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
    List<CityInfo> actualCityInfoList =
        travelRepository.findWillTravelOrderByStartAtAsc(givenUserList.get(0).getId(), 10);

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

  private List<City> createGivenCityList(Users users) {
    return cityRepository.saveAll(
        List.of(
            City.builder().name("서울").introContent("여기는 서울!").users(users).build(),
            City.builder().name("수원").introContent("여기는 수원!").users(users).build(),
            City.builder().name("부산").introContent("여기는 부산!").users(users).build()));
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
