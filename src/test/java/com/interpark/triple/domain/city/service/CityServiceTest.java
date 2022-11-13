package com.interpark.triple.domain.city.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.CityRepository;
import com.interpark.triple.domain.city.dto.CityInfoResponse;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

  @InjectMocks private CityService cityService;

  @Mock private CityRepository cityRepository;

  @DisplayName("city를 등록하는 메서드")
  @ParameterizedTest(name = "{index} {displayName} input:{0} answer:{1} ")
  @MethodSource("giveCityNameAndIntroContent")
  void registerCityTest(String givenCityName, String givenCityIntroContent) {
    // given
    CityRegisterRequest givenRequest =
        CityRegisterRequest.builder()
            .cityName(givenCityName)
            .cityIntroContent(givenCityIntroContent)
            .build();
    CityInfoResponse expectedResponse =
        CityInfoResponse.builder().name(givenCityName).introContent(givenCityIntroContent).build();

    City expectReturnCity =
        City.builder().name(givenCityName).introContent(givenCityIntroContent).build();

    // when
    when(cityRepository.save(any())).thenReturn(expectReturnCity);
    CityInfoResponse actualResponse = cityService.registerCity(givenRequest);

    // then
    assertAll(
        () -> assertEquals(expectedResponse.getName(), actualResponse.getName()),
        () -> assertEquals(expectedResponse.getIntroContent(), actualResponse.getIntroContent()));
  }

  private static Stream<Arguments> giveCityNameAndIntroContent() {
    return Stream.of(
        Arguments.of("도시", "간단한 도시 소개"),
        Arguments.of("한국", "김치의 도시"),
        Arguments.of("이탈리아", "파스타의 도시"));
  }
}
