package com.interpark.triple.domain.user.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private String name;

}
