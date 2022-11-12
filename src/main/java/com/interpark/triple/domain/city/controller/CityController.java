package com.interpark.triple.domain.city.controller;

import com.interpark.triple.domain.city.dto.CityInfoResponse;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import com.interpark.triple.domain.city.dto.CityUpdateRequest;
import com.interpark.triple.domain.city.service.CityService;
import com.interpark.triple.global.response.ResultCode;
import com.interpark.triple.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/city")
@RestController
@RequiredArgsConstructor
public class CityController {

  private final CityService cityService;

  @PostMapping
  public ResponseEntity<ResultResponse> registerCity(@Valid CityRegisterRequest request) {
    CityInfoResponse cityInfoResponse = cityService.registerCity(request);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.CITY_REGISTER_SUCCESS, cityInfoResponse));
  }
  @PutMapping
  public ResponseEntity<ResultResponse> updateCity(@Valid CityUpdateRequest request) {
    CityInfoResponse cityInfoResponse = cityService.updateCityInfo(request);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.CITY_UPDATE_SUCCESS, cityInfoResponse));
  }


}
