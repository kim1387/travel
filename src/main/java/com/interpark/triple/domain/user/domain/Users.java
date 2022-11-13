package com.interpark.triple.domain.user.domain;

import com.interpark.triple.global.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

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
}
