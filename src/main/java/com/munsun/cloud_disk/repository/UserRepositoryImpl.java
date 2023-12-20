package com.munsun.cloud_disk.repository;

import com.munsun.cloud_disk.model.Role;
import com.munsun.cloud_disk.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{
    Map<String,User> users;

    public UserRepositoryImpl() {
        this.users = Map.of("admin", new User("admin","admin", Role.ADMIN));
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return Optional.of(users.get(login));
    }
}
