package com.munsun.cloud_disk;

import com.munsun.cloud_disk.model.enums.Role;
import com.munsun.cloud_disk.model.User;
import com.munsun.cloud_disk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class CloudDiskApplication implements CommandLineRunner {
    private final UserRepository userRepository;

    @Value("${security.access.admin.username}")
    private String adminUsername;

    @Value("${security.access.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByLogin(adminUsername).isEmpty())
            userRepository.save(new User(null, adminUsername, adminPassword, Role.ADMIN));
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudDiskApplication.class, args);
    }
}