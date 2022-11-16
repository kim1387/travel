package com.interpark.triple.domain.city.domain.repository;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.city.exception.NotFoundCityEntityException;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.global.config.JpaAuditingConfig;
import com.interpark.triple.global.config.QuerydslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
    assertEquals(expectedCity.getName(), actualCity.getName());
    assertEquals(expectedCity.getIntroContent(), actualCity.getIntroContent());
  }

  @Test
  @DisplayName("하루 이내에 등록된 도시를 가장 최근에 등록한 순으로 조회")
  void findCityInfoRegisterTodayOrderByCreatedAtTest() {
    // given
    Users givenUser = usersRepository.save(Users.builder().name("기현").role(ROLE_USER).build());
    List<City> givenCityList = createGivenCityListWithFor(givenUser);
    createGivenCityCreatedTwoDaysAgo(givenUser);

    // when
    List<CityInfo> actualCityInfoList =
        cityRepository.findCityInfoRegisterTodayOrderByCreatedAt(1L, 10);

    City ac = cityRepository.findCityById(1L).orElseThrow(NotFoundCityEntityException::new);
    City ac1 = cityRepository.findCityById(2L).orElseThrow(NotFoundCityEntityException::new);
    City ac2 = cityRepository.findCityById(3L).orElseThrow(NotFoundCityEntityException::new);
    System.out.println(ac.getName() + ac.getCreatedDate() + ac.getUpdatedDate());
    System.out.println(ac1.getName() + ac1.getCreatedDate() + ac1.getUpdatedDate());
    System.out.println(ac2.getName() + ac2.getCreatedDate() + ac2.getUpdatedDate());

    for (CityInfo city : actualCityInfoList) {
      System.out.println(city.getName() + city.getCreatedAt());
    }
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

  private void createGivenCityCreatedTwoDaysAgo(Users givenUser) {
    City cityCreatedTwoDaysAgo =
        City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build();
    cityCreatedTwoDaysAgo.setCreatedAt(now().minusDays(2));
    cityRepository.save(cityCreatedTwoDaysAgo);
  }

  private List<City> createGivenCityListWithFor(Users givenUser) {
    List<City> cityList =
        List.of(
            City.builder().name("서울").introContent("여기는 서울!").users(givenUser).build(),
            City.builder().name("수원").introContent("여기는 수원!").users(givenUser).build(),
            City.builder().name("부산").introContent("여기는 부산!").users(givenUser).build());
    cityRepository.save(cityList.get(0));
    cityRepository.save(cityList.get(1));
    cityRepository.save(cityList.get(2));
    return cityList;
  }
}
