package ru.sspo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AttendanceManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AttendanceManagerApplication.class, args);
    }
}
