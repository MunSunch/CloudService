package com.munsun.cloud_disk.service.services.integration;

import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.exception.AuthenticationException;
import com.munsun.cloud_disk.exception.JwtFilterAuthException;
import com.munsun.cloud_disk.model.enums.Role;
import com.munsun.cloud_disk.model.User;
import com.munsun.cloud_disk.repository.UserRepository;
import com.munsun.cloud_disk.service.AuthService;
import com.munsun.cloud_disk.service.PostgresContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AuthServiceImplIntegrationTests extends PostgresContainer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @Test
    @Transactional
    public void failedAuthentication_InvalidPassword() {
        var expected = new LoginPasswordDtoIn("testLogin", "testPassword");
        var testUser = new User(1, "testLogin", "password", Role.USER);
            userRepository.save(testUser);
        Assertions.assertThrowsExactly(JwtFilterAuthException.class, () -> authService.authenticate(expected));
    }

    @Test
    @Transactional
    public void successAuthentication() throws AuthenticationException {
        var expected = new LoginPasswordDtoIn("testLogin", "testPassword");
        var testUser = new User(null, "testLogin", "testPassword", Role.USER);
        userRepository.save(testUser);
        var actual = authService.authenticate(expected);
        Assertions.assertNotNull(actual);
    }
}