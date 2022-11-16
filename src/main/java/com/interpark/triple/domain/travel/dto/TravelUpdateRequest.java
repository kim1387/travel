package com.interpark.triple.domain.travel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.*;

@Builder
@Getter
@RequiredArgsConstructor
public class TravelUpdateRequest {

    private final Long travelId;

    private final Long cityId;

    private final Long userId;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private final LocalDateTime travelStartAt;

    @Future(message = "여행 종료일은 미래만 허용")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private final LocalDateTime travelEndAt;

}
