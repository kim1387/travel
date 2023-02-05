package com.kim1387.travel.domain.travel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class TravelCreateRequest {

  @NotNull(message = "city Id는 필수입니다.")
  private final Long cityId;

  @NotNull(message = "로그인 userID 는 필수입니다.")
  private final Long userId;

  @NotNull(message = "여행 시작일은 필수입니다.")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private final LocalDateTime travelStartAt;

  @NotNull(message = "여행 종료일은 필수입니다.")
  @Future(message = "여행 종료일은 미래만 허용")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private final LocalDateTime travelEndAt;
}
