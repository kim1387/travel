package com.interpark.triple.domain.travel.domain.repository.querydsl;

import com.interpark.triple.domain.city.dto.CityInfo;
import com.interpark.triple.domain.city.dto.QCityInfo;
import com.interpark.triple.domain.travel.dto.QTravelInfo;
import com.interpark.triple.domain.travel.dto.TravelInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.interpark.triple.domain.city.domain.entity.QCity.city;
import static com.interpark.triple.domain.travel.domain.entity.QTravel.travel;
import static com.interpark.triple.domain.user.domain.entity.QUsers.users;
import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
public class TravelRepositoryQuerydslImpl implements TravelRepositoryQuerydsl {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<TravelInfo> findCurrentTravelOrderByStartAt(Long userId) {

    return queryFactory
        .select(new QTravelInfo(travel.city.name, travel.users.name, travel.startAt, travel.endAt))
        .from(travel)
        .where(
            (travel.isActivated.eq(true))
                .and((travel.users.id.eq(userId)))
                .and(isTodayBetweenStartAtAndEndAt()))
        .innerJoin(travel.users, users)
        .on(travel.users.id.eq(userId))
        .innerJoin(travel.city, city)
        .orderBy(travel.startAt.desc())
        .limit(10)
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
                travel.updatedDate))
        .from(travel)
        .where(
            (travel.isActivated.eq(true))
                .and((travel.users.id.eq(userId)))
                .and(isWillTravelStartAt()))
        .innerJoin(travel.users, users)
        .on(travel.users.id.eq(userId))
        .innerJoin(travel.city, city)
        .orderBy(travel.startAt.asc())
        .limit(limit)
        .fetch();
  }

  private static BooleanExpression isTodayBetweenStartAtAndEndAt() {
    return travel.startAt.between(
        Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", now()),
        Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", travel.endAt));
  }

  private static BooleanExpression isWillTravelStartAt() {
    return travel.startAt.after(Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", now()));
  }
}
