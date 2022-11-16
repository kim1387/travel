package com.interpark.triple.domain.city.controller;

import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.city.dto.CityInfoList;
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
    CityInfo cityInfo = cityService.registerCity(request);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_CITY_SUCCESS, cityInfo));
  }

  @PutMapping
  public ResponseEntity<ResultResponse> updateCity(@Valid CityUpdateRequest request) {
    CityInfo cityInfo = cityService.updateCityInfo(request);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_CITY_SUCCESS, cityInfo));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResultResponse> deleteCity(@PathVariable Long id) {
    cityService.deleteCity(id);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_CITY_SUCCESS, new Object()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResultResponse> findCityById(@PathVariable Long id) {
    CityInfo cityInfo = cityService.findCityInfoById(id);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ONE_CITY_SUCCESS, cityInfo));
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ResultResponse> findCityByUserId(@PathVariable Long userId) {
    CityInfoList cityInfoList = cityService.findCityInfoByUserIdWithConditions(userId);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USERS_CITY_SUCCESS, cityInfoList));
  }
}
