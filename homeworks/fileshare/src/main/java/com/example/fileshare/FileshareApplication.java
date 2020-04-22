package com.example.fileshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;




@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FileshareApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileshareApplication.class, args);
    }
}
