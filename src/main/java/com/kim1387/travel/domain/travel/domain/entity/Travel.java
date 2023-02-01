package com.kim1387.travel.domain.travel.domain.entity;

import com.kim1387.travel.domain.city.domain.entity.City;
import com.kim1387.travel.domain.travel.dto.TravelUpdateRequest;
import com.kim1387.travel.domain.user.domain.entity.Users;
import com.kim1387.travel.global.domain.BaseEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "travel")
public class Travel extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id", nullable = false)
  private Users users;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city_id", nullable = false)
  private City city;

  @Column(name = "start_at", nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startAt;

  @Column(name = "end_at", nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endAt;

  @Builder
  public Travel(Users users, City city, LocalDateTime startAt, LocalDateTime endAt) {
    this.users = users;
    this.city = city;
    this.startAt = startAt;
    this.endAt = endAt;
    this.isActivated = true;
  }

  public boolean isCanceled() {
    return !this.isActivated;
  }

  public void updateTravelInfo(TravelUpdateRequest request, City foundCityForUpdate) {
    this.city = foundCityForUpdate;
    this.startAt = request.getTravelStartAt();
    this.endAt = request.getTravelEndAt();
  }

  public void deleteTravel() {
    this.isActivated = false;
  }
}
