package com.interpark.triple.domain.users;

import com.interpark.triple.domain.user.dto.UserCreateRequest;

public class UserFixtures {

  public static final UserCreateRequest USER_GIHYUN_CREATE_REQUEST =
      UserCreateRequest.builder().name("김기현").build();
  public static final UserCreateRequest USER_YOUNGHAN_CREATE_REQUEST =
      UserCreateRequest.builder().name("김영한").build();
}
