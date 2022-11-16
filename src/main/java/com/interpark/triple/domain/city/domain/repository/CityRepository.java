package com.interpark.triple.domain.city.domain.repository;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.city.domain.repository.querydsl.CityRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>, CityRepositoryQuerydsl {

  @Query(value = "select c from City c where c.id = :id and c.isActivated = true")
  Optional<City> findCityById(@Param("id") Long id);

  @Query(
      value =
          "select c from City c join fetch c.travelList t where c.id = :id and c.isActivated = true and t.isActivated = true")
  Optional<City> findCityWithTravelById(@Param("id") Long id);
}
