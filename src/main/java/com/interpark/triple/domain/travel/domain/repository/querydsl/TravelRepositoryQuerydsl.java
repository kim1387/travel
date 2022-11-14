package com.interpark.triple.domain.travel.domain.repository.querydsl;

import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.travel.dto.TravelInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelRepositoryQuerydsl {

  List<TravelInfo> findCurrentTravelOrderByStartAt(@Param("id") Long id);


  List<CityInfo> findWillTravelOrderByStartAtAsc(Long userId, Integer limit);

}
