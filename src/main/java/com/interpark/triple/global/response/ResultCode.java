package com.interpark.triple.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

  // user
  USER_CREATE_SUCCESS("U001", "사용자 생성 성공"),

  // city
  CITY_REGISTER_SUCCESS("C001", "도시 등록 성공"),
  CITY_UPDATE_SUCCESS("C002", "도시 수정 성공"),
  CITY_DELETE_SUCCESS("C003", "도시 삭제 성공"),
  GET_ONE_CITY_SUCCESS("C004", "단일 도시 조회 성공"),
  ;

  private final String code;
  private final String message;
}
