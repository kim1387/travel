package com.interpark.triple.domain.users.acceptance;

import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.domain.users.acceptance.step.UserAcceptanceStep;
import com.interpark.triple.global.acceptance.BaseAcceptanceTest;
import com.interpark.triple.global.acceptance.step.AcceptanceStep;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.interpark.triple.domain.user.domain.entity.UsersRole.ROLE_USER;
import static com.interpark.triple.domain.users.UserFixtures.USER_GIHYUN_CREATE_REQUEST;
import static com.interpark.triple.domain.users.acceptance.step.UserAcceptanceStep.*;

@DisplayName("User 인수/통합 테스트")
class UserAcceptanceTest extends BaseAcceptanceTest {

  @Autowired UsersRepository usersRepository;

  @DisplayName("User 를 생성한다.")
  @Test
  void createUserTest() {
    // given

    // when
    ExtractableResponse<Response> response = requestToCreateUser(USER_GIHYUN_CREATE_REQUEST);

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    UserAcceptanceStep.assertThatUserInfo(response);
  }

  @DisplayName("User 를 단일 조회 한다.")
  @Test
  void findOneUserTest() {
    // given
    Users givenUsers = usersRepository.save(Users.builder().name("김기현").role(ROLE_USER).build());
    requestToCreateUser(USER_GIHYUN_CREATE_REQUEST);

    // when
    ExtractableResponse<Response> response = requestToFindOneUser(givenUsers.getId());

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    UserAcceptanceStep.assertThatUserInfo(response);
  }
}
