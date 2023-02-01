package com.kim1387.travel.domain.city.controller;

import com.kim1387.travel.domain.city.dto.CityInfo;
import com.kim1387.travel.domain.city.dto.CityInfoList;
import com.kim1387.travel.domain.city.dto.CityRegisterRequest;
import com.kim1387.travel.domain.city.dto.CityUpdateRequest;
import com.kim1387.travel.domain.city.service.CityService;
import com.kim1387.travel.global.response.ResultCode;
import com.kim1387.travel.global.response.ResultResponse;
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
  public ResponseEntity<ResultResponse> registerCity(
      @Valid @RequestBody CityRegisterRequest request) {
    CityInfo cityInfo = cityService.registerCity(request);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_CITY_SUCCESS, cityInfo));
  }

  @PutMapping
  public ResponseEntity<ResultResponse> updateCity(@Valid @RequestBody CityUpdateRequest request) {
    CityInfo cityInfo = cityService.updateCityInfo(request);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_CITY_SUCCESS, cityInfo));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResultResponse> deleteCity(@PathVariable Long id) {
    cityService.deleteCity(id);
    return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_CITY_SUCCESS, ""));
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
