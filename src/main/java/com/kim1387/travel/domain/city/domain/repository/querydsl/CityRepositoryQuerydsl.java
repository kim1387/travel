package com.kim1387.travel.domain.city.domain.repository.querydsl;

import com.kim1387.travel.domain.city.dto.CityInfo;
import java.util.List;

public interface CityRepositoryQuerydsl {

  List<CityInfo> findCityInfoRegisterTodayOrderByCreatedAt(Long userId, Integer limit);

  List<CityInfo> findCityInfoIfViewDuringSevenDaysOrderByRecentlyView(Long userId, Integer limit);

  List<CityInfo> findCityInfoById(Long userId, Integer limit);
}
