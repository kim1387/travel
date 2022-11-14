package com.interpark.triple.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Enum Naming Format : {행위}_{목적어}_{성공여부} message format: 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

  // user
  USER_CREATE_SUCCESS("U001", "사용자 생성 성공"),

  // city
  CREATE_CITY_SUCCESS("C001", "도시 등록 성공"),
  UPDATE_CITY_SUCCESS("C002", "도시 수정 성공"),
  DELETE_CITY_SUCCESS("C003", "도시 삭제 성공"),
  GET_ONE_CITY_SUCCESS("C004", "도시 단일 조회 성공"),
  GET_USERS_CITY_SUCCESS("C005", "사용자별 도시 조회 성공"),

  // travel
  CREATE_TRAVEL_SUCCESS("T001", "여행 등록 성공"),
  UPDATE_TRAVEL_SUCCESS("T002", "여행 수정 성공"),
  DELETE_TRAVEL_SUCCESS("T003", "여행 삭제 성공"),
  GET_ONE_TRAVEL_SUCCESS("T003", "여행 단일 조회 성공"),
  ;

  private final String code;
  private final String message;
}
