package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Реализация для тестирование через консоль
 */
@Service
@RequiredArgsConstructor
public class TestingServiceConsoleImpl implements TestingService {

    private final PrintService printService;

    @Override
    public String getFirstName() {
        var scanner = new Scanner(System.in);
        String input;
        do {
            System.out.print("First name: ");
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                System.out.println("First name is required");
            }
        } while (!StringUtils.hasText(input));
        return input.trim();
    }

    @Override
    public String getLastName() {
        var scanner = new Scanner(System.in);
        String input;
        do {
            System.out.print("Last name: ");
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                System.out.println("Last name is required");
            }
        } while (!StringUtils.hasText(input));
        return input.trim();
    }

    @Override
    public List<Answer> getAnswers(List<Question> questions) {
        var scanner = new Scanner(System.in);
        List<Answer> answers = new ArrayList<>();
        for (Question question : questions) {
            printService.print(question);
            final var optionsSize = question.getOptions().size();
            Integer selectedOptionNumber = null;
            do {
                System.out.print("Select only one option number: ");
                String input = scanner.nextLine();
                try {
                    selectedOptionNumber = Integer.parseInt(input);
                } catch (Exception e) {
                    System.out.println("Option number must be an integer");
                    continue;
                }
                if (selectedOptionNumber < 1 || selectedOptionNumber > optionsSize) {
                    selectedOptionNumber = null;
                    System.out.printf("Option number must be in range of 1..%s%n", optionsSize);
                }
            } while (selectedOptionNumber == null);
            answers.add(
                    new Answer(question, selectedOptionNumber));
        }
        return answers;
    }
}
