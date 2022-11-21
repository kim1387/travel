package com.interpark.triple.domain.city.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.CityRepository;
import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.city.dto.CityInfoList;
import com.interpark.triple.domain.city.dto.CityRegisterRequest;
import com.interpark.triple.domain.city.dto.CityUpdateRequest;
import com.interpark.triple.domain.city.exception.CantDeleteCityIfTravelExistException;
import com.interpark.triple.domain.city.exception.NotFoundCityEntityException;
import com.interpark.triple.domain.travel.domain.repository.TravelRepository;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.domain.user.exception.NotFoundUserEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    City foundCity =
        cityRepository.findCityById(id).orElseThrow(NotFoundCityEntityException::new);
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

  public CityInfoList findCityInfoByUserIdWithConditions(Long userId) {
    CityInfoList cityInfoList = new CityInfoList();

    List<CityInfo> cityInfosCurrentTraveling =
        travelRepository.findCurrentTravelOrderByStartAt(userId, USERS_CITY_READ_OFFSET);
    cityInfoList.addAllCityInfo(cityInfosCurrentTraveling);

    Integer remainLimitSize = USERS_CITY_READ_OFFSET - cityInfosCurrentTraveling.size();
    if (remainLimitSize > 0) {
      Set<CityInfo> exposeCityIfoListWithOutDuplication =
          getExposeCityIfoListWithOutDuplication(userId, remainLimitSize);
      cityInfoList.addAllCityInfo(
          exposeCityIfoListWithOutDuplication.stream()
              .limit(remainLimitSize)
              .collect(Collectors.toList()));
    }

    return cityInfoList;
  }

  private Set<CityInfo> getExposeCityIfoListWithOutDuplication(
      Long userId, Integer remainLimitSize) {
    Integer standardRemainLimitSize = remainLimitSize;
    List<CityInfo> cityInfosWillTravelOrderByStartAtAsc =
        travelRepository.findWillTravelOrderByStartAtAsc(userId, standardRemainLimitSize);
    Set<CityInfo> secondCityInfoHashSet = new LinkedHashSet<>(cityInfosWillTravelOrderByStartAtAsc);
    standardRemainLimitSize -= secondCityInfoHashSet.size();
    if (standardRemainLimitSize <= 0) {
      return secondCityInfoHashSet;
    }
    List<CityInfo> cityInfoRegisterTodayOrderByCreatedAt =
        cityRepository.findCityInfoRegisterTodayOrderByCreatedAt(userId, standardRemainLimitSize);
    secondCityInfoHashSet.addAll(cityInfoRegisterTodayOrderByCreatedAt);
    standardRemainLimitSize = remainLimitSize;
    standardRemainLimitSize -= secondCityInfoHashSet.size();
    if (standardRemainLimitSize <= 0) {
      return secondCityInfoHashSet;
    }
    List<CityInfo> cityInfoIfViewDuringSevenDaysOrderByRecentlyView =
        cityRepository.findCityInfoIfViewDuringSevenDaysOrderByRecentlyView(
            userId, standardRemainLimitSize);
    secondCityInfoHashSet.addAll(cityInfoIfViewDuringSevenDaysOrderByRecentlyView);
    standardRemainLimitSize = remainLimitSize;
    standardRemainLimitSize -= secondCityInfoHashSet.size();
    if (standardRemainLimitSize <= 0) {
      return secondCityInfoHashSet;
    }
    List<CityInfo> cityInfosElse = cityRepository.findCityInfoById(userId, standardRemainLimitSize);
    secondCityInfoHashSet.addAll(cityInfosElse);
    return secondCityInfoHashSet;
  }
}
