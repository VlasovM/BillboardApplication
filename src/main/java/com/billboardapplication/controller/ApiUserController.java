package com.billboardapplication.controller;

import com.billboardapplication.api.request.CommentRequest;
import com.billboardapplication.api.request.UserRegisterRequest;
import com.billboardapplication.api.response.UserRegisterResponse;
import com.billboardapplication.service.CommentService;
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
    private final CommentService commentService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(userService.registerUser(
                userRegisterRequest.getName(),
                userRegisterRequest.getEmail(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole()
        ));
    }

    @PostMapping("/comment")
    public ResponseEntity<Integer> sendComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.setCommentToAdvertisement(
                commentRequest.getParentId(),
                commentRequest.getAdvertisementId(),
                commentRequest.getText()
        ));
    }
}
