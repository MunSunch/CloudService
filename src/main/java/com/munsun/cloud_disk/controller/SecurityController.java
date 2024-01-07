package com.munsun.cloud_disk.controller;

import com.munsun.cloud_disk.dto.response.LoginPasswordDtoOut;
import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.exception.AuthenticationException;
import com.munsun.cloud_disk.exception.JwtFilterAuthException;
import com.munsun.cloud_disk.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class SecurityController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginPasswordDtoOut> authenticate(@RequestBody @Valid LoginPasswordDtoIn loginPassword) throws AuthenticationException {
        log.info("endpoint: POST /login");
        return ResponseEntity
                .ok(authService.authenticate(loginPassword));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        log.info("endpoint: POST /logout");
        authService.logout();
        return ResponseEntity
                .ok()
                .build();
    }
}
