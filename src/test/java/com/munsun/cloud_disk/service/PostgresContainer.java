package com.munsun.cloud_disk.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

public class PostgresContainer {
    @Container
    public static PostgreSQLContainer<?> container
            = new PostgreSQLContainer<>("postgres")
            .withUsername("root")
            .withPassword("root")
            .withReuse(true);

    @BeforeAll
    public static void start() {
        container.start();
    }

    @AfterAll
    public static void stop() {
        container.stop();
    }

    @DynamicPropertySource
    public static void configProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driverClassName", container::getDriverClassName);
    }
}
