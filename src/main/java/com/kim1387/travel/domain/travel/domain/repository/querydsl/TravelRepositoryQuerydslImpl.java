package com.kim1387.travel.domain.travel.domain.repository.querydsl;

import com.kim1387.travel.domain.city.dto.CityInfo;
import com.kim1387.travel.domain.city.dto.QCityInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.kim1387.travel.domain.city.domain.entity.QCity.city;
import static com.kim1387.travel.domain.travel.domain.entity.QTravel.travel;
import static com.kim1387.travel.domain.user.domain.entity.QUsers.users;
import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
public class TravelRepositoryQuerydslImpl implements TravelRepositoryQuerydsl {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<CityInfo> findCurrentTravelOrderByStartAt(Long userId, Integer limit) {

    return queryFactory
        .select(
            new QCityInfo(
                travel.city.name,
                travel.city.introContent,
                travel.city.createdDate,
                travel.city.updatedDate))
        .from(travel)
        .where(
            (travel.isActivated.eq(true))
                .and((travel.users.id.eq(userId)))
                .and(isTodayBetweenStartAtAndEndAt()))
        .innerJoin(travel.users, users)
        .on(travel.users.id.eq(userId))
        .innerJoin(travel.city, city)
        .on(travel.users.id.eq(userId))
        .orderBy(travel.startAt.desc())
        .limit(limit)
        .fetch();
  }

  @Override
  public List<CityInfo> findWillTravelOrderByStartAtAsc(Long userId, Integer limit) {
    return queryFactory
        .select(
            new QCityInfo(
                travel.city.name,
                travel.city.introContent,
                travel.city.createdDate,
                travel.city.updatedDate))
        .from(travel)
        .where(
            (travel.isActivated.eq(true))
                .and((travel.users.id.eq(userId)))
                .and(isWillTravelStartAt()))
        .innerJoin(travel.users, users)
        .on(travel.users.id.eq(userId))
        .innerJoin(travel.city, city)
            .on(travel.city.users.id.eq(userId))
            .orderBy(travel.startAt.asc())
        .limit(limit)
        .fetch();
  }

  private static BooleanExpression isTodayBetweenStartAtAndEndAt() {
    return Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", now())
        .between(
            Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", travel.startAt),
            Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", travel.endAt));
  }

  private static BooleanExpression isWillTravelStartAt() {
    return travel.startAt.after(Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", now()));
  }
}
