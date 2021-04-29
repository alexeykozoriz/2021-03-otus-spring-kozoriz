package ru.otus.kozoriz.homework20210324.service;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.kozoriz.homework20210324.domain.Question;

import java.util.List;

/**
 * Реализация для вывода в консоль
 */
public class QuestionsPrintServiceConsoleImpl implements QuestionsPrintService {
    @Override
    public void print(Question question) {
        if (question == null) {
            System.out.println("Empty question");
            return;
        }
        if (!StringUtils.hasText(question.getText())) {
            System.out.printf("%nEmpty question text%n");
            return;
        }
        if (!StringUtils.hasText(question.getNumber())) {
            System.out.printf("%nQuestion '%s' has empty number%n", question.getText());
            return;
        }
        if (CollectionUtils.isEmpty(question.getOptions())
                || question.getOptions().stream().noneMatch(StringUtils::hasText)) {
            System.out.printf("%nQuestion '%s) %s' has no options%n", question.getNumber(), question.getText());
            return;
        }
        System.out.printf("%n%s) %s%n", question.getNumber(), question.getText());
        for (var i = 0; i < question.getOptions().size(); i++) {
            System.out.printf("%s.%s) %s%n", question.getNumber(), i + 1, question.getOptions().get(i));
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
}
