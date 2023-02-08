package com.kim1387.travel.domain.city.service;

import com.kim1387.travel.domain.city.domain.entity.City;
import com.kim1387.travel.domain.city.domain.repository.CityRepository;
import com.kim1387.travel.domain.city.dto.CityInfo;
import com.kim1387.travel.domain.city.dto.CityInfoList;
import com.kim1387.travel.domain.city.dto.CityRegisterRequest;
import com.kim1387.travel.domain.city.dto.CityUpdateRequest;
import com.kim1387.travel.domain.city.exception.CantDeleteCityIfTravelExistException;
import com.kim1387.travel.domain.city.exception.NotFoundCityEntityException;
import com.kim1387.travel.domain.travel.domain.repository.TravelRepository;
import com.kim1387.travel.domain.user.domain.entity.Users;
import com.kim1387.travel.domain.user.domain.repository.UsersRepository;
import com.kim1387.travel.domain.user.exception.NotFoundUserEntityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

  private static final Integer USERS_CITY_READ_OFFSET = 10;

  private final CityRepository cityRepository;

  private final TravelRepository travelRepository;
  private final UsersRepository usersRepository;

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
    if (!foundCity.getTravelList().isEmpty()) {
      throw new CantDeleteCityIfTravelExistException();
    }
    foundCity.deleteCity();
  }

  public CityInfo findCityInfoById(Long id) {
    City foundCity = findCityById(id);
    foundCity.plusViewOne();
    cityRepository.save(foundCity);
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
    Users foundUser = getUserById(cityRegisterRequest.getUserId());

    return City.builder()
        .name(cityRegisterRequest.getCityName())
        .introContent(cityRegisterRequest.getCityIntroContent())
        .users(foundUser)
        .build();
  }

  private Users getUserById(Long usersId) {
    return usersRepository.findUsersById(usersId).orElseThrow(NotFoundUserEntityException::new);
  }

  public CityInfoList findCityInfoByUserId(Long userId) {

    List<CityInfo> cityInfosCurrentTraveling =
        travelRepository.findCurrentTravelOrderByStartAt(userId, USERS_CITY_READ_OFFSET);

    return new CityInfoList(cityInfosCurrentTraveling);
  }
}
