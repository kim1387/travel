package com.kim1387.travel.domain.city.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CityRegisterRequest {

  @NotNull(message = "userId는 필수입니다.")
  private final Long userId;

  @NotBlank(message = "도시이름을 입력해주세요.")
  private final String cityName;

  @NotBlank(message = "도시의 간단한 소개를 입력해주세요.")
  private final String cityIntroContent;
}
