package com.munsun.cloud_disk.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeFiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemTests {
//    @Container
//    private GenericContainer<?> app = new GenericContainer<>(
//            new DockerComposeFiles()
//    ).withExposedPorts(9090)
}
