package com.billboardapplication.controller;

import com.billboardapplication.api.request.UserRegisterRequest;
import com.billboardapplication.api.response.UserRegisterResponse;
import com.billboardapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiUserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(userService.registerUser(
                userRegisterRequest.getName(),
                userRegisterRequest.getEmail(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole()
        ));
    }


}
