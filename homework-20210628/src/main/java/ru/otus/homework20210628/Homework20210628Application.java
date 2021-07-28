package ru.otus.homework20210628;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class Homework20210628Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework20210628Application.class, args);
    }

}
