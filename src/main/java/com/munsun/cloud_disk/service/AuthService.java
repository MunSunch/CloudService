package com.munsun.cloud_disk.service;

import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.dto.response.LoginPasswordDtoOut;
import com.munsun.cloud_disk.exception.AuthenticationException;
import com.munsun.cloud_disk.exception.JwtFilterAuthException;

public interface AuthService {
    LoginPasswordDtoOut authenticate(LoginPasswordDtoIn loginPasswordDtoIn) throws AuthenticationException;
    void logout();
}
