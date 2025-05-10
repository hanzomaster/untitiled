package com.example.untitled;

import org.springframework.boot.SpringApplication;

public class TestuntitledApplication {

    public static void main(String[] args) {
        SpringApplication.from(com.example.untitled.UntitledApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
