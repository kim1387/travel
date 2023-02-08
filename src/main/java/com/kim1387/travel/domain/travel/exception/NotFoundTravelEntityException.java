package com.kim1387.travel.domain.travel.exception;

import com.kim1387.travel.global.exception.BusinessException;
import com.kim1387.travel.global.response.ErrorCode;

public class NotFoundTravelEntityException extends BusinessException {
  public NotFoundTravelEntityException() {
    super(ErrorCode.TRAVEL_NOT_FOUND);
  }
}
