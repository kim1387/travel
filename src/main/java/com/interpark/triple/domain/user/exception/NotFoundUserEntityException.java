package com.interpark.triple.domain.user.exception;

import com.interpark.triple.global.exception.BusinessException;
import com.interpark.triple.global.response.ErrorCode;

public class NotFoundUserEntityException extends BusinessException {

    public NotFoundUserEntityException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
