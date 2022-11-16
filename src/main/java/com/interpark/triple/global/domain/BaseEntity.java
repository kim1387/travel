package com.interpark.triple.global.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  @CreatedDate protected LocalDateTime createdDate;

  @LastModifiedDate private LocalDateTime updatedDate;

  @Getter
  @Column(name = "is_activated", nullable = false)
  protected boolean isActivated;

  public void activeOff() {
    this.isActivated = false;
  }
}
