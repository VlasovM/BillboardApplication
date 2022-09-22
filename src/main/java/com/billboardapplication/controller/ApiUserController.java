package com.billboardapplication.controller;

import com.billboardapplication.api.request.UserRegisterRequest;
import com.billboardapplication.api.response.UserResponse;
import com.billboardapplication.service.AdvertisementService;
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

    @PostMapping("user")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(userService.registerUser(
                userRegisterRequest.getName(),
                userRegisterRequest.getEmail(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole()
        ));
    }

}
