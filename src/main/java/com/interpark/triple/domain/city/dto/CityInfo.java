package com.interpark.triple.domain.city.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class CityInfo {

    private String name;

    private String introContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    @Builder
    @QueryProjection
    public CityInfo(String name, String introContent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.introContent = introContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityInfo cityInfo = (CityInfo) o;
        return Objects.equals(name, cityInfo.name) && Objects.equals(introContent, cityInfo.introContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, introContent);
    }
}
