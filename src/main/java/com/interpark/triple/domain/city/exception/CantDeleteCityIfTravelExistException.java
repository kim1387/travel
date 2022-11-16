package com.interpark.triple.domain.city.exception;

import com.interpark.triple.global.exception.BusinessException;
import com.interpark.triple.global.response.ErrorCode;

public class CantDeleteCityIfTravelExistException extends BusinessException {

    public CantDeleteCityIfTravelExistException() {
        super(ErrorCode.CITY_CANT_DELETE_TRAVEL_EXIST);
    }
}
