package com.billboardapplication.service;

import com.billboardapplication.api.response.LoginResponse;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginServiceTest {

    UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    AuthenticationManager mockManager = Mockito.mock(AuthenticationManager.class);
    LoginService underTestService = new LoginService(mockUserRepo, mockManager);

    @Test
    @DisplayName("Check auth user")
    void checkAuthUserTest() {
        LoginResponse expected = new LoginResponse();
        expected.set_successfully(true);
        expected.setCurrentUser(getUser().getEmail());

        User user = getUser();

        when(mockUserRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getEmail());

        LoginResponse actual = underTestService.checkUser();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Check not auth user")
    void checkNotAuthUserTest() {
        LoginResponse expected = new LoginResponse();
        expected.set_successfully(false);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("anonymousUser");

        LoginResponse actual = underTestService.checkUser();

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Login existing user in DB")
    void loginExistingUserTest() {
        LoginResponse expected = new LoginResponse();

        User user = getUser();
        String password = "12345Maks";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword(passwordEncoder.encode(password));

        expected.set_successfully(true);
        expected.setCurrentUser(user.getEmail());

        when(mockUserRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        LoginResponse actual = underTestService.login(user.getEmail(), password);
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Login not existing user in DB")
    void loginNotExistingUserTest() {
        LoginResponse expected = new LoginResponse();

        expected.set_successfully(false);

        LoginResponse actual = underTestService.login("NoExistingUser@mail.ru", "qwerty12345");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Logout")
    void logoutTest() {
        LoginResponse expected = new LoginResponse();
        expected.set_successfully(true);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("test@mail.ru");

        LoginResponse actual = underTestService.logout();

        assertEquals(expected, actual);
        assertThrows(NullPointerException.class, () -> SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private User getUser() {
        User user = new User();
        user.setName("Maxim");
        user.setEmail("MockEmail@Mock.ru");
        user.setPassword("1234");
        user.setRole("USER");

        return user;
    }

}