package com.interpark.triple.domain.travel.domain.entity;

import com.interpark.triple.domain.city.domain.entity.City;
import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.global.domain.BaseEntity;
import lombok.*;

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
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
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
}
