package ru.otus.homework20210407.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.domain.TestingResult;

import java.util.List;

/**
 * Реализация для вывода в консоль
 */
@Service
public class PrintServiceConsoleImpl implements PrintService {
    @Override
    public void print(Question question) {
        if (question == null) {
            System.out.println("Empty question");
            return;
        }
        if (!StringUtils.hasText(question.getText())) {
            System.out.println("Empty question text");
            return;
        }
        if (!StringUtils.hasText(question.getNumber())) {
            System.out.printf("Question '%s' has empty number%n", question.getText());
            return;
        }
        if (CollectionUtils.isEmpty(question.getOptions())
                || question.getOptions().stream().noneMatch(StringUtils::hasText)) {
            System.out.printf("Question '%s) %s' has no options%n", question.getNumber(), question.getText());
            return;
        }
        System.out.printf("%s) %s%n", question.getNumber(), question.getText());
        for (var i = 0; i < question.getOptions().size(); i++) {
            System.out.printf("%s) %s%n", i + 1, question.getOptions().get(i));
        }

    }

    @Override
    public void printAll(List<Question> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            System.out.println("Questions not found");
            return;
        }
        questions.forEach(this::print);
    }

    @Override
    public void print(TestingResult testingResult) {
        if (testingResult == null) {
            System.out.println("Testing result not found");
            return;
        }
        System.out.printf("%nTesting result for '%s %s': %s%n%n",
                testingResult.getFirstName(),
                testingResult.getLastName(),
                testingResult.isPassed() ? "SUCCESS" : "FAIL");
    }
}
