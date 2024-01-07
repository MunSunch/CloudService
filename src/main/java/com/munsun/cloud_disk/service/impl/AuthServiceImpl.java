package com.munsun.cloud_disk.service.impl;

import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.dto.response.LoginPasswordDtoOut;
import com.munsun.cloud_disk.exception.AuthenticationException;
import com.munsun.cloud_disk.exception.JwtFilterAuthException;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import com.munsun.cloud_disk.model.User;
import com.munsun.cloud_disk.repository.UserRepository;
import com.munsun.cloud_disk.security.JwtProvider;
import com.munsun.cloud_disk.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public LoginPasswordDtoOut authenticate(LoginPasswordDtoIn loginPasswordDtoIn) throws AuthenticationException {
        log.info("Try check credentials: login={}", loginPasswordDtoIn.login());
        try {
            User user = userRepository.findByLogin(loginPasswordDtoIn.login())
                    .orElseThrow(UserNotFoundException::new);
            if(user.getPassword().equals(loginPasswordDtoIn.password())) {
                log.info("Try check credentials success");
                return new LoginPasswordDtoOut(jwtProvider.generateAccessToken(loginPasswordDtoIn));
            }
            throw new AuthenticationException();
        } catch (UserNotFoundException e) {
            log.error("User not found");
            throw new AuthenticationException();
        }
    }

    @Override
    public void logout() {
        log.info("Try logout");
        SecurityContextHolder.clearContext();
    }
}