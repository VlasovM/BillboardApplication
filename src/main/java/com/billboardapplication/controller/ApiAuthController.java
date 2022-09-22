package com.billboardapplication.controller;

import com.billboardapplication.api.response.LoginResponse;
import com.billboardapplication.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final LoginService loginService;

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> checkUser(Principal principal) {
        return ResponseEntity.ok(loginService.checkUser(principal));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(String email, String password) {
        return ResponseEntity.ok(loginService.login(email, password));
    }

    @GetMapping("/logout")
    public ResponseEntity<LoginResponse> logout() {
        return ResponseEntity.ok(loginService.logout());
    }

}
