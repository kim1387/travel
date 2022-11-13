package com.interpark.triple.domain.city.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CityUpdateRequest {

  private final Long cityId;

  private final String cityName;

  private final String cityIntroContent;
}
