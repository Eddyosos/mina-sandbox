package com.github.eddyosos.minasandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MinaSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinaSandboxApplication.class, args);
    }

}
