package com.munsun.cloud_disk.security;

import com.munsun.cloud_disk.dto.in.LoginPasswordDtoIn;

public interface JwtProvider {
    boolean validateAccessToken(String token);
    String generateAccessToken(LoginPasswordDtoIn dto);
}
