package com.billboardapplication.controller;

import com.billboardapplication.api.response.LoginResponse;
import com.billboardapplication.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final LoginService loginService;

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> checkUser() {
        return ResponseEntity.ok(loginService.checkUser());
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(loginService.login(email, password));
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<LoginResponse> logout() {
        return ResponseEntity.ok(loginService.logout());
    }

}
