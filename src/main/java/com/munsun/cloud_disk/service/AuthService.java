package com.munsun.cloud_disk.service;

import com.munsun.cloud_disk.dto.in.LoginPasswordDtoIn;
import com.munsun.cloud_disk.dto.out.LoginPasswordDtoOut;
import com.munsun.cloud_disk.exception.AuthException;
import com.munsun.cloud_disk.exception.UserNotFoundException;

public interface AuthService {
    LoginPasswordDtoOut auth(LoginPasswordDtoIn loginPasswordDtoIn) throws UserNotFoundException, AuthException;
}
