package com.munsun.cloud_disk.service.repository;

import com.munsun.cloud_disk.model.enums.Role;
import com.munsun.cloud_disk.model.User;
import com.munsun.cloud_disk.repository.UserRepository;
import com.munsun.cloud_disk.service.PostgresContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryUnitTests extends PostgresContainer {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void successSaveNewUser() {
        var newUser = new User();
            newUser.setId(null);
            newUser.setLogin("login");
            newUser.setPassword("password");
            newUser.setRole(Role.USER);

        var actualId = userRepository.save(newUser).getId();
        var actualUser = userRepository.getReferenceById(actualId);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(actualUser.getId());
            Assertions.assertEquals(newUser.getLogin(), actualUser.getLogin());
            Assertions.assertEquals(newUser.getPassword(), actualUser.getPassword());
            Assertions.assertEquals(newUser.getRole(), actualUser.getRole());
        });
    }

    @Test
    @Transactional
    public void successSearchUserByLogin() {
        String expectedLogin = "test";
        var newUser = new User();
            newUser.setId(null);
            newUser.setLogin(expectedLogin);
            newUser.setPassword("password");
            newUser.setRole(Role.USER);

        var actualId = userRepository.save(newUser).getId();
        var actualUser = userRepository.findByLogin(expectedLogin);

        Assertions.assertTrue(actualUser.isPresent());

        var actualLogin = actualUser.get().getLogin();
        Assertions.assertEquals(expectedLogin, actualLogin);
    }

    @Test
    @Transactional
    public void failedSearchUserByLogin() {
        var actualUser = userRepository.findByLogin("don't exists");
        Assertions.assertTrue(actualUser.isEmpty());
    }
}
