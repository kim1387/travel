package com.interpark.triple.domain.city.exception;

import com.interpark.triple.global.exception.BusinessException;
import com.interpark.triple.global.response.ErrorCode;

public class NotFoundCityEntityException extends BusinessException {
    public NotFoundCityEntityException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
