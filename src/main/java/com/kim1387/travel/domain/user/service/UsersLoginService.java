package com.kim1387.travel.domain.user.service;

import com.kim1387.travel.domain.user.domain.entity.Users;
import com.kim1387.travel.domain.user.domain.entity.UsersRole;
import com.kim1387.travel.domain.user.domain.repository.UsersRepository;
import com.kim1387.travel.domain.user.dto.UserCreateRequest;
import com.kim1387.travel.domain.user.dto.UserInfo;
import com.kim1387.travel.domain.user.exception.NotFoundUserEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersLoginService {

  private final UsersRepository usersRepository;

  public Users findLoginUsersById(Long userId) {
    return usersRepository.findUsersById(userId).orElseThrow(NotFoundUserEntityException::new);
  }

  public UserInfo findOneUsersById(Long userId) {
    Users foundUsers =
        usersRepository.findUsersById(userId).orElseThrow(NotFoundUserEntityException::new);
    return mapUserToUserInfo(foundUsers);
  }

  public UserInfo registerUser(UserCreateRequest userCreateRequest) {
    Users newUsers =
        Users.builder().name(userCreateRequest.getName()).role(UsersRole.ROLE_USER).build();
    newUsers = usersRepository.save(newUsers);
    return mapUserToUserInfo(newUsers);
  }

  private static UserInfo mapUserToUserInfo(Users foundUsers) {
    return UserInfo.builder().name(foundUsers.getName()).role(foundUsers.getRole()).build();
  }
}
