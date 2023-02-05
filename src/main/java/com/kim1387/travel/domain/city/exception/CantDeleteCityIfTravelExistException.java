package com.kim1387.travel.domain.city.exception;

import com.kim1387.travel.global.exception.BusinessException;
import com.kim1387.travel.global.response.ErrorCode;

public class CantDeleteCityIfTravelExistException extends BusinessException {

    public CantDeleteCityIfTravelExistException() {
        super(ErrorCode.CITY_CANT_DELETE_TRAVEL_EXIST);
    }
}
