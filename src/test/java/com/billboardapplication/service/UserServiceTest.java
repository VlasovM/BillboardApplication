package com.billboardapplication.service;

import com.billboardapplication.api.response.UserRegisterResponse;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    UserService underTestService = new UserService(mockUserRepo);

    @Test
    @DisplayName("Register new user")
    void registerNewUserTest() {
        UserRegisterResponse expected = new UserRegisterResponse();
        expected.set_successfully(true);

        User user = getUser();
        when(mockUserRepo.save(user)).thenReturn(user);

        UserRegisterResponse actual = underTestService.registerUser("Maxim", "User@mail.ru",
                "12345", "USER");

        assertEquals(expected, actual);
    }

    private User getUser() {
        User user = new User();

        user.setName("Maxim");
        user.setPassword("123456");
        user.setRole("USER");
        user.setEmail("User@mail.ru");

        return user;
    }

}