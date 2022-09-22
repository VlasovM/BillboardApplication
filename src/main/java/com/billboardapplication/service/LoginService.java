package com.billboardapplication.service;

import com.billboardapplication.api.response.LoginResponse;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public LoginResponse checkUser(Principal principal) {
        LoginResponse loginResponse = new LoginResponse();
        if (principal == null) {
            return loginResponse;
        }

        loginResponse.set_successfully(true);
        loginResponse.setCurrentUser(principal.getName());
        return loginResponse;
    }

    public LoginResponse login(String email, String password) {
        LoginResponse loginResponse = new LoginResponse();

        if (!findUser(email, password)) {
            loginResponse.set_successfully(false);
            return loginResponse;
        }

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(auth);

        loginResponse.setCurrentUser(email);
        loginResponse.set_successfully(true);
        return loginResponse;
    }

    private boolean findUser(String email, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        Optional<User> user = userRepository.findByEmail(email);
        return user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();
    }

    public LoginResponse logout() {
        SecurityContextHolder.clearContext();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.set_successfully(true);
        return loginResponse;
    }

}
