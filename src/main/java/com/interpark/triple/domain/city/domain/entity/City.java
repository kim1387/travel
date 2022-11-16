package com.interpark.triple.domain.city.domain.entity;

import com.google.common.annotations.VisibleForTesting;
import com.interpark.triple.domain.city.dto.CityUpdateRequest;
import com.interpark.triple.domain.travel.domain.entity.Travel;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.global.domain.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "city")
public class City extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "intro_content", nullable = false)
  private String introContent;

  @Column(name = "view", nullable = false)
  private Integer view;

  @LastModifiedDate
  @Column(name = "latest_view_at", nullable = false)
  private LocalDateTime latestViewAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id", nullable = false)
  private Users users;

  @OneToMany(mappedBy = "city")
  private List<Travel> travelList = new ArrayList<>();

  @Builder
  public City(String name, String introContent, Users users) {
    this.name = name;
    this.introContent = introContent;
    this.users = users;
    this.view = 0;
    this.latestViewAt = now();
    this.isActivated = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    City city = (City) o;
    return Objects.equals(id, city.id)
        && Objects.equals(name, city.name)
        && Objects.equals(introContent, city.introContent)
        && Objects.equals(view, city.view);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, introContent, view);
  }

  public void updateCityInfo(CityUpdateRequest cityUpdateRequest) {
    this.name = cityUpdateRequest.getCityName();
    this.introContent = cityUpdateRequest.getCityIntroContent();
  }

  public void deleteCity() {
    this.activeOff();
  }

  @VisibleForTesting
  public void setCreatedAt(LocalDateTime localDateTime) {
    this.createdDate = localDateTime;
  }

  public void plusViewOne() {
    this.view = this.view + 1;
  }
}
