package com.munsun.cloud_disk.service.services;

import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.exception.AuthenticationException;
import com.munsun.cloud_disk.exception.JwtFilterAuthException;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import com.munsun.cloud_disk.model.enums.Role;
import com.munsun.cloud_disk.model.User;
import com.munsun.cloud_disk.repository.UserRepository;
import com.munsun.cloud_disk.security.JwtProvider;
import com.munsun.cloud_disk.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplUnitTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void failedAuthentication_InvalidPassword() throws UserNotFoundException, JwtFilterAuthException {
        var expected = new LoginPasswordDtoIn("testLogin", "testPassword");
        var testUser = new User(1, "testLogin", "password", Role.USER);

        Mockito.when(userRepository.findByLogin(expected.login()))
                .thenReturn(Optional.of(testUser));

        Assertions.assertThrowsExactly(JwtFilterAuthException.class, () -> authService.authenticate(expected));
    }

    @Test
    public void successAuthentication_ValidPassword() throws AuthenticationException {
        var expected = new LoginPasswordDtoIn("testLogin", "testPassword");
        var testUser = new User(1, "testLogin", "testPassword", Role.USER);

        Mockito.when(userRepository.findByLogin(expected.login()))
                .thenReturn(Optional.of(testUser));
        Mockito.when(jwtProvider.generateAccessToken(Mockito.any())).thenReturn("token");

        var actual = authService.authenticate(expected);
        Assertions.assertNotNull(actual.authToken());
    }
}
