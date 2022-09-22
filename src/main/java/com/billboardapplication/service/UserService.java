package com.billboardapplication.service;

import com.billboardapplication.api.response.UserResponse;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse registerUser(String name, String email, String password, String role) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);

        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setResult(true);
        return userResponse;
    }

}
