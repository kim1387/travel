package com.interpark.triple.domain.travel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class TravelCreateRequest {

  private final Long cityId;

  private final Long userId;

  private final LocalDateTime travelStartAt;

  @Future(message = "여행 종료일은 미래만 허용")
  private final LocalDateTime travelEndAt;
}
