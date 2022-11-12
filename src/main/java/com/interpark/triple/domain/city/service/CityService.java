package com.interpark.triple.domain.city.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.CityRepository;
import com.interpark.triple.domain.city.dto.CityInfoResponse;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import com.interpark.triple.domain.city.dto.CityUpdateRequest;
import com.interpark.triple.domain.city.exception.NotFoundCityEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
  private final CityRepository cityRepository;

  public CityInfoResponse registerCity(CityRegisterRequest cityRegisterRequest) {
    City newCity = convertCityFromRequest(cityRegisterRequest);
    City savedCity = cityRepository.save(newCity);

    return mapCityEntityToCityInfoResponse(savedCity);
  }

  public CityInfoResponse updateCityInfo(CityUpdateRequest cityUpdateRequest) {
    City foundCity =
        cityRepository
            .findCityById(cityUpdateRequest.getCityId())
            .orElseThrow(NotFoundCityEntityException::new);
    foundCity.updateCityInfo(cityUpdateRequest);
    City updatedCity = cityRepository.save(foundCity);
    return mapCityEntityToCityInfoResponse(updatedCity);
  }

  private CityInfoResponse mapCityEntityToCityInfoResponse(City savedCity) {
    return CityInfoResponse.builder()
        .name(savedCity.getName())
        .introContent(savedCity.getIntroContent())
        .createdAt(savedCity.getCreatedDate())
        .updatedAt(savedCity.getUpdatedDate())
        .build();
  }

  private City convertCityFromRequest(CityRegisterRequest cityRegisterRequest) {
    return City.builder()
        .name(cityRegisterRequest.getCityName())
        .introContent(cityRegisterRequest.getCityIntroContent())
        .build();
  }
}
