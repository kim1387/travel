package com.kim1387.travel.domain.city.exception;

import com.kim1387.travel.global.exception.BusinessException;
import com.kim1387.travel.global.response.ErrorCode;

public class NotFoundCityEntityException extends BusinessException {
  public NotFoundCityEntityException() {
    super(ErrorCode.CITY_NOT_FOUND);
  }
}
