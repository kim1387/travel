package com.kim1387.travel.domain.city.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CityInfoList {

  private List<CityInfo> cityInfos = new ArrayList<>();

  public void addAllCityInfo(List<CityInfo> cityInfos) {
    this.cityInfos.addAll(cityInfos);
  }

  public void addCityInfo(CityInfo cityInfo) {
    this.cityInfos.add(cityInfo);
  }
}
