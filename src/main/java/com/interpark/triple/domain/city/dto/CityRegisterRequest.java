package com.interpark.triple.domain.city.dto;

import lombok.*;

@Builder
@Getter
@RequiredArgsConstructor
public class CityRegisterRequest {

  private final String cityName;

  private final String cityIntroContent;
}
