package com.interpark.triple.domain.travel.domain.repository;

import com.interpark.triple.domain.travel.domain.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query(value = "select t from Travel t where t.id = :id and t.isActivated = true")
    Optional<Travel> findTravelById(@Param("id") Long id);
}
