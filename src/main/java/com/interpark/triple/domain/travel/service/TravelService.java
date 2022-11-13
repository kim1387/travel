package com.interpark.triple.domain.travel.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.service.CityService;
import com.interpark.triple.domain.travel.domain.entity.Travel;
import com.interpark.triple.domain.travel.domain.repository.TravelRepository;
import com.interpark.triple.domain.travel.dto.TravelCreateRequest;
import com.interpark.triple.domain.travel.dto.TravelInfo;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.service.UsersLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelService {

  private final TravelRepository travelRepository;

  private final UsersLoginService usersLoginService;

  private final CityService cityService;

  public TravelInfo createTravel(TravelCreateRequest request) {

    Travel newTravel = createNewTravelEntity(request);

    Travel savedTravel =
        travelRepository.save(newTravel);

    return mapTravelEntityToInfo(savedTravel);
  }

  private Travel createNewTravelEntity(
      TravelCreateRequest request) {
    Users loginUsers = usersLoginService.findLoginUsersById(request.getUserId());
    City foundCity = cityService.findCityById(request.getCityId());
    return mapCreateTravelRequestToEntity(request, loginUsers, foundCity);
  }

  private Travel mapCreateTravelRequestToEntity(
          TravelCreateRequest request, Users loginUsers, City foundCity) {
    return Travel.builder()
        .users(loginUsers)
        .city(foundCity)
        .startAt(request.getTravelStartAt())
        .endAt(request.getTravelEndAt())
        .build();
  }

  private TravelInfo mapTravelEntityToInfo(
      Travel savedTravel) {
    return TravelInfo.builder()
        .cityName(savedTravel.getCity().getName())
        .userName(savedTravel.getUsers().getName())
        .startTravelAt(savedTravel.getStartAt())
        .endTravelAt(savedTravel.getEndAt())
        .isCanceled(savedTravel.isCanceled())
        .build();
  }


}
