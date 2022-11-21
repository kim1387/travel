package com.interpark.triple.domain.users;

import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.dto.UserCreateRequest;

import static com.interpark.triple.domain.user.domain.entity.UsersRole.ROLE_USER;

public class UserFixtures {

  public static final UserCreateRequest USER_GIHYUN_CREATE_REQUEST =
      UserCreateRequest.builder().name("김기현").build();
  public static final UserCreateRequest USER_YOUNGHAN_CREATE_REQUEST =
      UserCreateRequest.builder().name("김영한").build();
  public static final Users USER_GIHYUN_CREATE_ENTITY =
      Users.builder().name("김기현").role(ROLE_USER).build();
}
