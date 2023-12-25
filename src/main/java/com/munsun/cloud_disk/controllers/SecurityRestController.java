package com.munsun.cloud_disk.controllers;

import com.munsun.cloud_disk.dto.out.LoginPasswordDtoOut;
import com.munsun.cloud_disk.dto.in.LoginPasswordDtoIn;
import com.munsun.cloud_disk.exception.AuthException;
import com.munsun.cloud_disk.exception.UserNotFoundException;
import com.munsun.cloud_disk.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
public class SecurityRestController {
    private AuthService authService;

    @PostMapping("/login")
    public LoginPasswordDtoOut auth(@RequestBody LoginPasswordDtoIn loginPassword, Principal principal) throws UserNotFoundException, AuthException {
        log.info("endpoint: POST /login; user={}", principal.getName());
        return authService.auth(loginPassword);
    }

    @PostMapping("/logout")
    public void logout(@RequestParam("auth-token") String authToken, Principal principal) {
        log.info("endpoint: POST /logout; user={}", principal.getName());
        SecurityContextHolder.clearContext();
    }
}
