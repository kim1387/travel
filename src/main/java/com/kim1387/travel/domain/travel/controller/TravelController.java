package com.kim1387.travel.domain.travel.controller;

import com.kim1387.travel.domain.travel.dto.TravelCreateRequest;
import com.kim1387.travel.domain.travel.dto.TravelInfo;
import com.kim1387.travel.domain.travel.dto.TravelUpdateRequest;
import com.kim1387.travel.domain.travel.service.TravelService;
import com.kim1387.travel.global.response.ResultCode;
import com.kim1387.travel.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/travel")
@RestController
@RequiredArgsConstructor
public class TravelController {

  private final TravelService travelService;

  @PostMapping
  public ResponseEntity<ResultResponse> createTravel(
      @Valid @RequestBody TravelCreateRequest request) {
    TravelInfo travelInfo = travelService.createTravel(request);

    return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_TRAVEL_SUCCESS, travelInfo));
  }

  @PutMapping
  public ResponseEntity<ResultResponse> updateTravel(
      @Valid @RequestBody TravelUpdateRequest request) {
    TravelInfo updatedTravelInfo = travelService.updateTravel(request);

    return ResponseEntity.ok(
        ResultResponse.of(ResultCode.UPDATE_TRAVEL_SUCCESS, updatedTravelInfo));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResultResponse> updateTravel(@PathVariable Long id) {
    travelService.deleteTravel(id);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_TRAVEL_SUCCESS, ""));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResultResponse> findOneTravelById(@PathVariable Long id) {
    TravelInfo foundTravelInfo = travelService.findOneTravelById(id);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ONE_TRAVEL_SUCCESS, foundTravelInfo));
  }
}
