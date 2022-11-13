package com.interpark.triple.domain.reservation.controller;

import com.interpark.triple.domain.reservation.dto.TravelReservationCreateRequest;
import com.interpark.triple.domain.reservation.dto.TravelReservationInfo;
import com.interpark.triple.domain.reservation.service.TravelReservationService;
import com.interpark.triple.global.response.ResultCode;
import com.interpark.triple.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/travel/reservation")
@RestController
@RequiredArgsConstructor
public class TravelReservationController {

  private final TravelReservationService travelReservationService;

  @PostMapping
  public ResponseEntity<ResultResponse> createTravelReservation(
      TravelReservationCreateRequest request) {
    TravelReservationInfo travelReservationinfo =
        travelReservationService.createTravelReservation(request);

    return ResponseEntity.ok(
        ResultResponse.of(ResultCode.CREATE_TRAVEL_RESERVATION_SUCCESS, travelReservationinfo));
  }


}
