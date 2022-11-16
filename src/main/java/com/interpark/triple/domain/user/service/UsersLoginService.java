package com.interpark.triple.domain.user.service;

import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.entity.UsersRole;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.domain.user.dto.UserCreateRequest;
import com.interpark.triple.domain.user.exception.NotFoundUserEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersLoginService {

    private final UsersRepository usersRepository;

    public Users findLoginUsersById(Long userId){
        return usersRepository.findUsersById(userId).orElseThrow(NotFoundUserEntityException::new);
    }


    public Users registerUser(UserCreateRequest userCreateRequest){
        Users newUsers = Users.builder().name(userCreateRequest.getName()).role(UsersRole.ROLE_USER).build();
        return usersRepository.save(newUsers);
    }
}
