package com.interpark.triple.domain.travel.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.service.CityService;
import com.interpark.triple.domain.travel.domain.entity.Travel;
import com.interpark.triple.domain.travel.domain.repository.TravelRepository;
import com.interpark.triple.domain.travel.dto.TravelCreateRequest;
import com.interpark.triple.domain.travel.dto.TravelInfo;
import com.interpark.triple.domain.travel.dto.TravelUpdateRequest;
import com.interpark.triple.domain.travel.exception.NotFoundTravelEntityException;
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

    Travel savedTravel = travelRepository.save(newTravel);

    return mapTravelEntityToInfo(savedTravel);
  }

  private Travel createNewTravelEntity(TravelCreateRequest request) {
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

  private TravelInfo mapTravelEntityToInfo(Travel savedTravel) {
    return TravelInfo.builder()
        .cityName(savedTravel.getCity().getName())
        .userName(savedTravel.getUsers().getName())
        .startTravelAt(savedTravel.getStartAt())
        .endTravelAt(savedTravel.getEndAt())
        .isCanceled(savedTravel.isCanceled())
        .build();
  }

  public TravelInfo updateTravel(TravelUpdateRequest request) {

    Travel foundTravel = findTravelWithCityAndUsersById(request.getTravelId());
    City foundCityForUpdate = cityService.findCityById(request.getCityId());

    foundTravel.updateTravelInfo(request, foundCityForUpdate);
    Travel savedTravel = travelRepository.save(foundTravel);

    return mapTravelEntityToInfo(savedTravel);
  }

  public Travel findTravelWithCityAndUsersById(Long id) {
    return travelRepository
        .findTravelWithCityAndUsersById(id)
        .orElseThrow(NotFoundTravelEntityException::new);
  }

  public void deleteTravel(Long id) {
    Travel foundTravel = findTravelById(id);
    foundTravel.deleteTravel();
  }

  private Travel findTravelById(Long id) {
    return travelRepository.findTravelById(id).orElseThrow(NotFoundTravelEntityException::new);
  }

  public TravelInfo findOneTravelById(Long id) {

    Travel foundTravel = findTravelWithCityAndUsersById(id);
    return mapTravelEntityToInfo(foundTravel);
  }

}
