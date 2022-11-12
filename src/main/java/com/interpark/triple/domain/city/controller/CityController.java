package com.interpark.triple.domain.city.controller;

import com.interpark.triple.domain.city.dto.CityInfoResponse;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import com.interpark.triple.domain.city.service.CityService;
import com.interpark.triple.global.response.ResultCode;
import com.interpark.triple.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
