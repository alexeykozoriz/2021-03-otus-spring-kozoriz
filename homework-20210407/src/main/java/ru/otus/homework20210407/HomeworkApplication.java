package ru.otus.homework20210407;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.homework20210407.domain.TestingResult;
import ru.otus.homework20210407.service.AnswerEvaluatingService;
import ru.otus.homework20210407.service.PrintService;
import ru.otus.homework20210407.service.QuestionsService;
import ru.otus.homework20210407.service.TestingService;

@SpringBootApplication
@RequiredArgsConstructor
public class HomeworkApplication implements CommandLineRunner {

    private final AnswerEvaluatingService answerEvaluatingService;
    private final TestingService testingService;
    private final QuestionsService questionsService;
    private final PrintService printService;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            printService.print(TestingResult.builder()
                    .firstName(testingService.getFirstName())
                    .lastName(testingService.getLastName())
                    .isPassed(
                            answerEvaluatingService.isAllAnswersRight(
                                    testingService.getAnswers(
                                            questionsService.findAllQuestions())))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
