package com.munsun.cloud_disk;

import com.munsun.cloud_disk.model.enums.Role;
import com.munsun.cloud_disk.model.User;
import com.munsun.cloud_disk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudDiskApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByLogin("admin").isEmpty())
            userRepository.save(new User(null, "admin", "admin", Role.ADMIN));
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudDiskApplication.class, args);
    }
}