package com.interpark.triple.domain.city.domain.repository;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.global.config.QuerydslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.interpark.triple.domain.user.domain.entity.UsersRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(QuerydslConfig.class)
class CityRepositoryTest {

  @Autowired private CityRepository cityRepository;
  @Autowired private UsersRepository usersRepository;

  @Test
  @DisplayName("city 저장 함수 test")
  void saveCityTest() {
    // given
    Users givenUser = usersRepository.save(Users.builder().name("기현").role(ROLE_USER).build());
    City expectedCity = City.builder().name("도시").introContent("간단한 도시 소개").users(givenUser).build();

    // when
    City actualCity = cityRepository.save(expectedCity);

    // then
    assertEquals(expectedCity, actualCity);
  }

  @Test
  @DisplayName("하루 이내에 등록된 도시를 가장 최근에 등록한 순으로 조회")
  void findCityInfoRegisterTodayOrderByCreatedAtTest() {
    // given
    Users givenUser = usersRepository.save(Users.builder().name("기현").role(ROLE_USER).build());
    List<City> givenCityList = createGivenCityListWithFor(givenUser);
    // 순서대로 save되기 때문에 최근 등록순 == givenList 순
    // when
    List<CityInfo> actualCityInfoList =
        cityRepository.findCityInfoRegisterTodayOrderByCreatedAt(1L, 10);

    // then
    assertAll(
        () -> assertEquals(3, actualCityInfoList.size()),
        () -> assertEquals(givenCityList.get(0).getName(), actualCityInfoList.get(0).getName()),
        () ->
            assertEquals(
                givenCityList.get(0).getIntroContent(),
                actualCityInfoList.get(0).getIntroContent()),
        () ->
            assertEquals(
                givenCityList.get(1).getName(), actualCityInfoList.get(1).getName()),
        () ->
            assertEquals(
                givenCityList.get(1).getIntroContent(),
                actualCityInfoList.get(1).getIntroContent()),
        () ->
            assertEquals(
                givenCityList.get(2).getName(), actualCityInfoList.get(2).getName()),
        () ->
            assertEquals(
                givenCityList.get(2).getIntroContent(),
                actualCityInfoList.get(2).getIntroContent()));
  }

  private List<City> createGivenCityListWithFor(Users givenUser) {
    List<City> cityList =
        List.of(
            City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build(),
            City.builder().name("수원").introContent("여기는 수원!").users(givenUser).build(),
            City.builder().name("부산").introContent("여기는 부산!").users(givenUser).build());
    cityList.forEach(city -> cityRepository.save(city));
    return cityList;
  }
}
