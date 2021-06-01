package ru.otus.homework20210407;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.homework20210407.service.RunnerService;

@SpringBootApplication
@RequiredArgsConstructor
public class HomeworkApplication implements CommandLineRunner {

    private final RunnerService runnerService;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        runnerService.runTesting();
    }
}
