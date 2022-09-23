package com.billboardapplication.service;

import com.billboardapplication.api.response.UserRegisterResponse;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserRegisterResponse registerUser(String name, String email, String password, String role) {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        userRegisterResponse.set_successfully(true);
        return userRegisterResponse;
    }

}
