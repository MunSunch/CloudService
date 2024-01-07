package com.munsun.cloud_disk.service;

import com.munsun.cloud_disk.dto.request.LoginPasswordDtoIn;
import com.munsun.cloud_disk.dto.response.LoginPasswordDtoOut;
import com.munsun.cloud_disk.exception.AuthException;
import com.munsun.cloud_disk.exception.UserNotFoundException;

public interface AuthService {
    LoginPasswordDtoOut authenticate(LoginPasswordDtoIn loginPasswordDtoIn) throws UserNotFoundException, AuthException;
    void logout(String authToken);
}
