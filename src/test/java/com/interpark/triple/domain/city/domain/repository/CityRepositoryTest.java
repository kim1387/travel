package com.interpark.triple.domain.city.domain.repository;

import com.interpark.triple.domain.city.domain.entity.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CityRepositoryTest {

  @Autowired private CityRepository cityRepository;

  @Test
  @DisplayName("city 저장 함수 test")
  void saveCityTest() {
    // given
    City expectedCity = City.builder().name("도시").introContent("간단한 도시 소개").build();

    // when
    City actualCity = cityRepository.save(expectedCity);

    // then
    assertEquals(expectedCity, actualCity);
  }
}
