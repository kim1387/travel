package com.interpark.triple.domain.city.domain.repository;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.global.config.JpaAuditingConfig;
import com.interpark.triple.global.config.QuerydslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static com.interpark.triple.domain.user.domain.entity.UsersRole.ROLE_USER;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({QuerydslConfig.class, JpaAuditingConfig.class})
class CityRepositoryTest {

  @Autowired private CityRepository cityRepository;
  @Autowired private UsersRepository usersRepository;

  @Test
  @DisplayName("city 저장 함수 test")
  void saveCityTest() {
    // given
    Users givenUser = usersRepository.save(Users.builder().name("기현").role(ROLE_USER).build());
    City expectedCity =
        City.builder().name("도시").introContent("간단한 도시 소개").users(givenUser).build();

    // when
    City actualCity = cityRepository.save(expectedCity);

    // then
    assertAll(
        () -> assertEquals(expectedCity.getName(), actualCity.getName()),
        () -> assertEquals(expectedCity.getIntroContent(), actualCity.getIntroContent()));
  }

  @Test
  @DisplayName("하루 이내에 등록된 도시를 가장 최근에 등록한 순으로 조회")
  void findCityInfoRegisterTodayOrderByCreatedAtTest() {
    // given
    Users givenUser = usersRepository.save(Users.builder().name("기현").role(ROLE_USER).build());
    List<City> givenCityList = createGivenCityListWithFor(givenUser);
    createGivenCityCreatedInputDaysAgo(givenUser, 8);

    // when
    List<CityInfo> actualCityInfoList =
        cityRepository.findCityInfoRegisterTodayOrderByCreatedAt(givenUser.getId(), 10);

    // then
    assertAll(
        () -> assertEquals(3, actualCityInfoList.size()),
        () -> assertEquals(givenCityList.get(0).getName(), actualCityInfoList.get(0).getName()),
        () ->
            assertEquals(
                givenCityList.get(0).getIntroContent(),
                actualCityInfoList.get(0).getIntroContent()),
        () -> assertEquals(givenCityList.get(1).getName(), actualCityInfoList.get(1).getName()),
        () ->
            assertEquals(
                givenCityList.get(1).getIntroContent(),
                actualCityInfoList.get(1).getIntroContent()),
        () -> assertEquals(givenCityList.get(2).getName(), actualCityInfoList.get(2).getName()),
        () ->
            assertEquals(
                givenCityList.get(2).getIntroContent(),
                actualCityInfoList.get(2).getIntroContent()));
  }

  @Test
  @DisplayName("최근 일주일 이내에 한 번 이상 조회된 도시, 가장 최근에 조회한 것부터 조회")
  void findCityInfoIfViewDuringSevenDaysOrderByRecentlyViewTest() {
    // given
    Users givenUser = usersRepository.save(Users.builder().name("기현").role(ROLE_USER).build());
    List<City> givenCityList = createGivenCityListWithFor(givenUser);
    createGivenCityCreatedInputDaysAgo(givenUser, 2);
    createGivenCityNoView(givenUser);
    // when
    List<CityInfo> actualCityInfoList =
        cityRepository.findCityInfoIfViewDuringSevenDaysOrderByRecentlyView(givenUser.getId(), 10);

    // then
    assertAll(
        () -> assertEquals(3, actualCityInfoList.size()),
        () -> assertEquals(givenCityList.get(0).getName(), actualCityInfoList.get(0).getName()),
        () ->
            assertEquals(
                givenCityList.get(0).getIntroContent(),
                actualCityInfoList.get(0).getIntroContent()),
        () -> assertEquals(givenCityList.get(1).getName(), actualCityInfoList.get(1).getName()),
        () ->
            assertEquals(
                givenCityList.get(1).getIntroContent(),
                actualCityInfoList.get(1).getIntroContent()),
        () -> assertEquals(givenCityList.get(2).getName(), actualCityInfoList.get(2).getName()),
        () ->
            assertEquals(
                givenCityList.get(2).getIntroContent(),
                actualCityInfoList.get(2).getIntroContent()));
  }

  private void createGivenCityCreatedInputDaysAgo(Users givenUser, Integer agoDays) {
    City cityCreatedTwoDaysAgo =
        City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build();
    cityCreatedTwoDaysAgo = cityRepository.save(cityCreatedTwoDaysAgo);
    cityCreatedTwoDaysAgo.setCreatedAt(now().minusDays(agoDays));
    cityRepository.save(cityCreatedTwoDaysAgo);
  }

  @Test
  @DisplayName("id로 cityInfo 조회하는 함수 구현")
  void findCityInfoByIdWithLimitTest() {
    // given
    Users givenUser = usersRepository.save(Users.builder().name("기현").role(ROLE_USER).build());
    List<City> givenCityList = createGivenCityListWithFor(givenUser);
    System.out.println(givenUser.getId());
    // when
    List<CityInfo> actualCityInfoById = cityRepository.findCityInfoById(givenUser.getId(), 2);

    // then
    assertAll(
        () -> assertEquals(2, actualCityInfoById.size()),
        () -> assertEquals(givenCityList.get(0).getName(), actualCityInfoById.get(0).getName()),
        () ->
            assertEquals(
                givenCityList.get(0).getIntroContent(),
                actualCityInfoById.get(0).getIntroContent()),
        () -> assertEquals(givenCityList.get(1).getName(), actualCityInfoById.get(1).getName()),
        () ->
            assertEquals(
                givenCityList.get(1).getIntroContent(),
                actualCityInfoById.get(1).getIntroContent()));
  }

  private List<City> createGivenCityListWithFor(Users givenUser) {
    List<City> cityList =
        Arrays.asList(
            City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build(),
            City.builder().name("수원").introContent("여기는 수원!").users(givenUser).build(),
            City.builder().name("부산").introContent("여기는 부산!").users(givenUser).build());
    cityList.get(0).plusViewOne();
    cityList.get(1).plusViewOne();
    cityList.get(2).plusViewOne();
    cityRepository.save(cityList.get(0));
    cityRepository.save(cityList.get(1));
    cityRepository.save(cityList.get(2));
    return cityList;
  }

  private City createGivenCityNoView(Users givenUser) {
    City city = City.builder().name("광주").introContent("여기는 광주!").users(givenUser).build();
    cityRepository.save(city);
    return city;
  }
}
