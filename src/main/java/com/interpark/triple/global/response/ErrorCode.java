package com.interpark.triple.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum Naming Format : {주체}_{이유}
 * message format: 동사 명사형으로 마무리
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global
  INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),

  // USER
  USER_NOT_FOUND(400, "U001", "유저를 찾기 실패"),

  // CITY
  CITY_NOT_FOUND(400, "C001", "도시를 찾을 수 없음"),
  CITY_CANT_DELETE_TRAVEL_EXIST(400, "C002", "연관된 여행이 있어서 도시를 삭제할 수 없음"),

  // TRAVEL
  TRAVEL_NOT_FOUND(400, "T001", "여행 계획을 찾을 수 없음"),


  ;

  private final int status;
  private final String code;
  private final String message;
}
