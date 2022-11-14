package com.interpark.triple.domain.travel.domain.repository;

import com.interpark.triple.domain.travel.domain.entity.Travel;
import com.interpark.triple.domain.travel.domain.repository.querydsl.TravelRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long>, TravelRepositoryQuerydsl {

  @Query(
      value =
          "select t from Travel t join fetch t.users join fetch t.city where t.id = :id and t.isActivated = true")
  Optional<Travel> findTravelWithCityAndUsersById(@Param("id") Long id);

  @Query("select t from Travel t where t.id = :id and t.isActivated = true")
  Optional<Travel> findTravelById(@Param("id") Long id);

  @Query(
      value =
          "select t "
        + "from Travel t "
          + "join fetch t.users "
          + "join fetch t.city "
        + "where "
          + "t.users.id = :userId and "
          + "t.isActivated = true and "
          + "t.startAt between current_time and t.endAt "
        + "order by t.startAt")
  List<Travel> findCurrentTravelOrderByStartAtByUserId(@Param("userId") Long userId);

}
