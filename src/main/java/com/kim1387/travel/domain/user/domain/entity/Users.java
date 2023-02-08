package com.kim1387.travel.domain.user.domain.entity;

import com.kim1387.travel.global.domain.BaseEntity;
import java.util.Objects;
import javax.persistence.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class Users extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private UsersRole role;

  @Builder
  public Users(String name, UsersRole role) {
    this.name = name;
    this.role = role;
    super.isActivated = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Users users = (Users) o;
    return Objects.equals(id, users.id) && Objects.equals(name, users.name) && role == users.role;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, role);
  }
}
