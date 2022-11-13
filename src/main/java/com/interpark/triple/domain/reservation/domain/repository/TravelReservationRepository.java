package com.interpark.triple.domain.reservation.domain.repository;

import com.interpark.triple.domain.reservation.domain.entity.TravelReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelReservationRepository extends JpaRepository<TravelReservation, Long> {}
