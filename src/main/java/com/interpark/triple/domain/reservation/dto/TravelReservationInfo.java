package com.interpark.triple.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class TravelReservationInfo {

  private Long reservationId;

  private String cityName;

  private String userName;

  private LocalDateTime startTravelAt;

  private LocalDateTime endTravelAt;

  private boolean isCanceled;
}
