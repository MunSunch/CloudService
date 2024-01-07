package com.munsun.cloud_disk.controller;

import com.munsun.cloud_disk.dto.response.LoginPasswordDtoOut;
import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.exception.AuthException;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import com.munsun.cloud_disk.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Slf4j
@RestController
public class SecurityController {
    private AuthService authService;

    @PostMapping("/login")
    public LoginPasswordDtoOut authenticate(@RequestBody LoginPasswordDtoIn loginPassword) throws UserNotFoundException, AuthException {
        log.info("endpoint: POST /login");
        return authService.authenticate(loginPassword);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam("auth-token") String authToken) {
        log.info("endpoint: POST /logout");
        authService.logout(authToken);
        return ResponseEntity
                .noContent()
                .build();
    }
}
