package com.interpark.triple.domain.city.domain.entity;

import com.interpark.triple.global.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

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

  @Builder
  public City(String name, String introContent) {
    this.name = name;
    this.introContent = introContent;
    this.view = 0;
    updateActivated(true);
  }
}
