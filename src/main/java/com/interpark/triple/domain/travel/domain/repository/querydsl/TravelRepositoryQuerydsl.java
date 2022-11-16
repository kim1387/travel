package com.interpark.triple.domain.travel.domain.repository.querydsl;

import com.interpark.triple.domain.city.dto.CityInfo;

import java.util.List;

public interface TravelRepositoryQuerydsl {

  List<CityInfo> findCurrentTravelOrderByStartAt(Long id, Integer limit);

  List<CityInfo> findWillTravelOrderByStartAtAsc(Long userId, Integer limit);
}
