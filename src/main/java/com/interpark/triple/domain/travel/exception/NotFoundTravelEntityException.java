package com.interpark.triple.domain.travel.exception;

import com.interpark.triple.global.exception.BusinessException;
import com.interpark.triple.global.response.ErrorCode;

public class NotFoundTravelEntityException extends BusinessException {
    public NotFoundTravelEntityException() {
        super(ErrorCode.TRAVEL_NOT_FOUND);
    }
}
