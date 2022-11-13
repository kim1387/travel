package com.interpark.triple.domain.city.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CityInfoResponse {

    private String name;

    private String introContent;

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

}
