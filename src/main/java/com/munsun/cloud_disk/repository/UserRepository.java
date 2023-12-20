package com.munsun.cloud_disk.repository;

import com.munsun.cloud_disk.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findUserByLogin(String login);
}
