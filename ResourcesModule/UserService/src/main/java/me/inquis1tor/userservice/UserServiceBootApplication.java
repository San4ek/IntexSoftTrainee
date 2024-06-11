package me.inquis1tor.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceBootApplication.class,args);
    }
}
