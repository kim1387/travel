package com.kim1387.travel.domain.user.controller;

import com.kim1387.travel.domain.user.dto.UserCreateRequest;
import com.kim1387.travel.domain.user.dto.UserInfo;
import com.kim1387.travel.domain.user.service.UsersLoginService;
import com.kim1387.travel.global.response.ResultCode;
import com.kim1387.travel.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UsersLoginService usersLoginService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<ResultResponse> registerUsers(@RequestBody UserCreateRequest request){
        UserInfo userInfo = usersLoginService.registerUser(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_CREATE_SUCCESS, userInfo));
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<ResultResponse> getUsers(@PathVariable Long id){
        UserInfo userInfo = usersLoginService.findOneUsersById(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_SUCCESS, userInfo));
    }
}
