package com.interpark.triple.domain.user.service;

import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.domain.repository.UsersRepository;
import com.interpark.triple.domain.user.exception.NotFoundUserEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersLoginService {

    private final UsersRepository usersRepository;

    public Users findLoginUsersById(Long userId){
        return usersRepository.findUserById(userId).orElseThrow(NotFoundUserEntityException::new);
    }
}
