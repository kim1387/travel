package com.interpark.triple.domain.travel.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class TravelInfo {

  private final String cityName;

  private final String userName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private final LocalDateTime startTravelAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private final LocalDateTime endTravelAt;

  @Builder
  @QueryProjection
  public TravelInfo(
      String cityName, String userName, LocalDateTime startTravelAt, LocalDateTime endTravelAt) {
    this.cityName = cityName;
    this.userName = userName;
    this.startTravelAt = startTravelAt;
    this.endTravelAt = endTravelAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TravelInfo that = (TravelInfo) o;
    return Objects.equals(cityName, that.cityName)
        && Objects.equals(userName, that.userName)
        && Objects.equals(startTravelAt.getSecond(), that.startTravelAt.getSecond())
        && Objects.equals(endTravelAt.getSecond(), that.endTravelAt.getSecond());
  }

  @Override
  public int hashCode() {
    return Objects.hash(cityName, userName, startTravelAt.getSecond(), endTravelAt.getSecond());
  }
}
