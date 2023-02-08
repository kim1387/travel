package com.kim1387.travel.domain.user.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
  private String name;
}
