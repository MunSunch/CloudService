package com.munsun.cloud_disk.service.impl;

import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.dto.response.LoginPasswordDtoOut;
import com.munsun.cloud_disk.exception.AuthException;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import com.munsun.cloud_disk.model.User;
import com.munsun.cloud_disk.repository.UserRepository;
import com.munsun.cloud_disk.security.JwtProvider;
import com.munsun.cloud_disk.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    @Override
    public LoginPasswordDtoOut authenticate(LoginPasswordDtoIn loginPasswordDtoIn) throws UserNotFoundException, AuthException {
        log.info("Try check credentials: login={}", loginPasswordDtoIn.login());
        User user = userRepository.findByLogin(loginPasswordDtoIn.login())
                .orElseThrow(UserNotFoundException::new);
        if(user.getPassword().equals(loginPasswordDtoIn.password())) {
            log.info("Try check credentials success");
            return new LoginPasswordDtoOut(jwtProvider.generateAccessToken(loginPasswordDtoIn));
        }
        throw new AuthException(0, "Bad credentials");
    }

    @Override
    public void logout(String authToken) {
        log.info("Try logout token={}", authToken);
        SecurityContextHolder.clearContext();
    }
}
