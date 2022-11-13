package com.interpark.triple.domain.city.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.CityRepository;
import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import com.interpark.triple.domain.city.dto.CityUpdateRequest;
import com.interpark.triple.domain.city.exception.NotFoundCityEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
  private final CityRepository cityRepository;

  public CityInfo registerCity(CityRegisterRequest cityRegisterRequest) {
    City newCity = convertCityFromRequest(cityRegisterRequest);
    City savedCity = cityRepository.save(newCity);

    return mapCityEntityToCityInfoResponse(savedCity);
  }

  public CityInfo updateCityInfo(CityUpdateRequest cityUpdateRequest) {
    City foundCity = findCityById(cityUpdateRequest.getCityId());
    foundCity.updateCityInfo(cityUpdateRequest);
    City updatedCity = cityRepository.save(foundCity);
    return mapCityEntityToCityInfoResponse(updatedCity);
  }

  public void deleteCity(Long id) {
    City foundCity = cityRepository.findCityById(id).orElseThrow(NotFoundCityEntityException::new);
    foundCity.deleteCity();
  }

  public CityInfo findCityInfoById(Long id) {
    City foundCity = findCityById(id);
    return mapCityEntityToCityInfoResponse(foundCity);
  }

  public City findCityById(Long id) {
    return cityRepository.findCityById(id).orElseThrow(NotFoundCityEntityException::new);
  }


  private CityInfo mapCityEntityToCityInfoResponse(City savedCity) {
    return CityInfo.builder()
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
