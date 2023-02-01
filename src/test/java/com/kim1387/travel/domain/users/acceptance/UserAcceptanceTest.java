package com.kim1387.travel.domain.users.acceptance;

import com.kim1387.travel.domain.user.domain.entity.Users;
import com.kim1387.travel.domain.user.domain.repository.UsersRepository;
import com.kim1387.travel.domain.users.acceptance.step.UserAcceptanceStep;
import com.kim1387.travel.global.acceptance.BaseAcceptanceTest;
import com.kim1387.travel.global.acceptance.step.AcceptanceStep;
import com.kim1387.travel.domain.user.domain.entity.UsersRole;
import com.kim1387.travel.domain.users.UserFixtures;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("User 인수/통합 테스트")
class UserAcceptanceTest extends BaseAcceptanceTest {

  @Autowired
  UsersRepository usersRepository;

  @DisplayName("User 를 생성한다.")
  @Test
  void createUserTest() {
    // given

    // when
    ExtractableResponse<Response> response = UserAcceptanceStep.requestToCreateUser(UserFixtures.USER_GIHYUN_CREATE_REQUEST);

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    UserAcceptanceStep.assertThatUserInfo(response);
  }

  @DisplayName("User 를 단일 조회 한다.")
  @Test
  void findOneUserTest() {
    // given
    Users givenUsers = usersRepository.save(Users.builder().name("김기현").role(UsersRole.ROLE_USER).build());
    UserAcceptanceStep.requestToCreateUser(UserFixtures.USER_GIHYUN_CREATE_REQUEST);

    // when
    ExtractableResponse<Response> response = UserAcceptanceStep.requestToFindOneUser(givenUsers.getId());

    // then
    AcceptanceStep.assertThatStatusIsOk(response);
    UserAcceptanceStep.assertThatUserInfo(response);
  }
}
