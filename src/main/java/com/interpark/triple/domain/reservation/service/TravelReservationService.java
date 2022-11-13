package com.interpark.triple.domain.reservation.service;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.service.CityService;
import com.interpark.triple.domain.reservation.domain.entity.TravelReservation;
import com.interpark.triple.domain.reservation.domain.repository.TravelReservationRepository;
import com.interpark.triple.domain.reservation.dto.TravelReservationCreateRequest;
import com.interpark.triple.domain.reservation.dto.TravelReservationInfo;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.service.UsersLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelReservationService {

  private final TravelReservationRepository travelReservationRepository;

  private final UsersLoginService usersLoginService;

  private final CityService cityService;

  public TravelReservationInfo createTravelReservation(TravelReservationCreateRequest request) {

    TravelReservation newTravelReservation = createNewTravelReservationEntity(request);

    TravelReservation savedTravelReservation =
        travelReservationRepository.save(newTravelReservation);

    return mapTravelReservationEntityToInfo(savedTravelReservation);
  }

  private TravelReservation createNewTravelReservationEntity(
      TravelReservationCreateRequest request) {
    Users loginUsers = usersLoginService.findLoginUsersById(request.getUserId());
    City foundCity = cityService.findCityById(request.getCityId());
    return mapCreateTravelReservationRequestToEntity(request, loginUsers, foundCity);
  }

  private TravelReservation mapCreateTravelReservationRequestToEntity(
      TravelReservationCreateRequest request, Users loginUsers, City foundCity) {
    return TravelReservation.builder()
        .users(loginUsers)
        .city(foundCity)
        .startAt(request.getTravelStartAt())
        .endAt(request.getTravelEndAt())
        .build();
  }

  private TravelReservationInfo mapTravelReservationEntityToInfo(
      TravelReservation savedTravelReservation) {
    return TravelReservationInfo.builder()
        .reservationId(savedTravelReservation.getId())
        .cityName(savedTravelReservation.getCity().getName())
        .userName(savedTravelReservation.getUsers().getName())
        .startTravelAt(savedTravelReservation.getStartAt())
        .endTravelAt(savedTravelReservation.getEndAt())
        .isCanceled(savedTravelReservation.isCanceled())
        .build();
  }
}
