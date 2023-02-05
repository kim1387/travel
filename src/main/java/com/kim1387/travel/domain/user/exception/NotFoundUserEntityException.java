package com.kim1387.travel.domain.user.exception;

import com.kim1387.travel.global.exception.BusinessException;
import com.kim1387.travel.global.response.ErrorCode;

public class NotFoundUserEntityException extends BusinessException {

    public NotFoundUserEntityException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
