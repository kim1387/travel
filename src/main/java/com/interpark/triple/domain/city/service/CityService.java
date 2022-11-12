package com.interpark.triple.domain.city.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.CityRepository;
import com.interpark.triple.domain.city.dto.CityInfoResponse;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
  private final CityRepository cityRepository;

  public CityInfoResponse registerCity(CityRegisterRequest cityRegisterRequest) {
    City newCity = mapCityEntityFromRequest(cityRegisterRequest);
    City savedCity = cityRepository.save(newCity);

    return mapCityEntityToCityInfoResponse(savedCity);
  }

  private CityInfoResponse mapCityEntityToCityInfoResponse(City savedCity) {
    return CityInfoResponse.builder()
            .name(savedCity.getName())
            .introContent(savedCity.getIntroContent())
            .createdAt(savedCity.getCreatedDate())
            .updatedAt(savedCity.getUpdatedDate())
            .build();
  }

  private City mapCityEntityFromRequest(CityRegisterRequest cityRegisterRequest) {
    return City.builder()
        .name(cityRegisterRequest.getCityName())
        .introContent(cityRegisterRequest.getCityIntroContent())
        .build();
  }
}
