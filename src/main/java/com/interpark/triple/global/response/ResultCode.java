package com.interpark.triple.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

  // user
  USER_CREATE_SUCCESS("U001", "사용자 생성 성공"),

  // city
  CITY_REGISTER_SUCCESS("C001", "도시 등록 성공")

  ;

  private final String code;
  private final String message;
}
