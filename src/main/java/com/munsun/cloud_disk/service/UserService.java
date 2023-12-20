package com.munsun.cloud_disk.service;

import com.munsun.cloud_disk.model.User;

public interface UserService {
    User find(String login);
}
