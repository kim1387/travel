package com.kim1387.travel.domain.city;

import com.kim1387.travel.domain.city.dto.CityRegisterRequest;

public class CityFixtures {

  public static final CityRegisterRequest CITY_SUWON_CREATE_REQUEST =
      CityRegisterRequest.builder().userId(1L).cityName("수원").cityIntroContent("간단한 수원 소개").build();

  public static final CityRegisterRequest CITY_SEOUL_CREATE_REQUEST =
      CityRegisterRequest.builder().userId(1L).cityName("서울").cityIntroContent("간단한 서울 소개").build();
}
