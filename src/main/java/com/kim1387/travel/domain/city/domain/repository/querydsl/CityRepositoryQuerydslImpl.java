package com.kim1387.travel.domain.city.domain.repository.querydsl;

import com.kim1387.travel.domain.city.dto.CityInfo;
import com.kim1387.travel.domain.city.dto.QCityInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.kim1387.travel.domain.city.domain.entity.QCity.city;
import static com.kim1387.travel.domain.user.domain.entity.QUsers.users;
import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
public class CityRepositoryQuerydslImpl implements CityRepositoryQuerydsl {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<CityInfo> findCityInfoRegisterTodayOrderByCreatedAt(Long userId, Integer limit) {

    return queryFactory
        .select(new QCityInfo(city.name, city.introContent, city.createdDate, city.updatedDate))
        .from(city)
        .where((city.isActivated.eq(true)).and(isCreatedAtToday()).and((city.users.id.eq(userId))))
        .innerJoin(city.users, users)
        .on(city.users.id.eq(userId))
        .orderBy(city.createdDate.asc())
        .limit(limit)
        .fetch();
  }

  @Override
  public List<CityInfo> findCityInfoIfViewDuringSevenDaysOrderByRecentlyView(
      Long userId, Integer limit) {
    return queryFactory
        .select(new QCityInfo(city.name, city.introContent, city.createdDate, city.updatedDate))
        .from(city)
        .where(
            (city.isActivated.eq(true))
                .and(isViewDuringSevenDays())
                .and(city.view.ne(0))
                .and((city.users.id.eq(userId))))
        .innerJoin(city.users, users)
        .on(city.users.id.eq(userId))
        .orderBy(city.latestViewAt.asc())
        .limit(limit)
        .fetch();
  }

  @Override
  public List<CityInfo> findCityInfoById(Long userId, Integer limit) {
    return queryFactory
        .select(new QCityInfo(city.name, city.introContent, city.createdDate, city.updatedDate))
        .from(city)
        .where((city.isActivated.eq(true)).and((city.users.id.eq(userId))))
        .innerJoin(city.users, users)
        .on(city.users.id.eq(userId))
        .limit(limit)
        .fetch();
  }

  private static BooleanExpression isCreatedAtToday() {
    return city.createdDate.after(
        Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", now().minusDays(1)));
  }

  private static BooleanExpression isViewDuringSevenDays() {
    return city.latestViewAt.after(
        Expressions.dateTimeTemplate(LocalDateTime.class, "{0}", now().minusDays(7)));
  }
}
