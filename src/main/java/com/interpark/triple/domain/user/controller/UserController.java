package com.interpark.triple.domain.user.controller;

import com.interpark.triple.domain.user.domain.entity.Users;
import com.interpark.triple.domain.user.dto.UserCreateRequest;
import com.interpark.triple.domain.user.service.UsersLoginService;
import com.interpark.triple.global.response.ResultCode;
import com.interpark.triple.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UsersLoginService usersLoginService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<ResultResponse> registerUsers(UserCreateRequest request){
        Users users = usersLoginService.registerUser(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_CREATE_SUCCESS, users));
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<ResultResponse> getUsers(@PathVariable Long id){
        Users users = usersLoginService.findLoginUsersById(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_SUCCESS, users));
    }
}
