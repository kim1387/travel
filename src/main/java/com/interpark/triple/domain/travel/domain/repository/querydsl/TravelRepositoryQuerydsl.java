package com.interpark.triple.domain.travel.domain.repository.querydsl;

import com.interpark.triple.domain.city.dto.CityInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelRepositoryQuerydsl {

  List<CityInfo> findCurrentTravelOrderByStartAt(@Param("id") Long id, Integer limit);

  List<CityInfo> findWillTravelOrderByStartAtAsc(Long userId, Integer limit);
}
