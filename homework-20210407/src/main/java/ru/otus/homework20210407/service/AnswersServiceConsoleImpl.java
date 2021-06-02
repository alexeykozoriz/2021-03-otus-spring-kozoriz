package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.AnswerByOption;
import ru.otus.homework20210407.domain.AnswerByText;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.error.ConsolePromptBuildingError;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Реализация для взаимодействия через консоль
 */
@Service
@RequiredArgsConstructor
public class AnswersServiceConsoleImpl implements AnswersService {

    @Override
    public List<Answer> getAnswers(List<Question> questions) throws Exception {
        List<Answer> results = new ArrayList<>();
        for (Question question : questions) {
            final var options = question.getOptions();
            final String prompt;
            try {
                prompt = getQuestionPrompt(question);
            } catch (ConsolePromptBuildingError consolePromptBuildingError) {
                throw consolePromptBuildingError.getInnerException();
            }
            if (CollectionUtils.isEmpty(options)) {
                results.add(new AnswerByText(question, getTextAnswer(prompt)));
            } else {
                results.add(new AnswerByOption(question, getOptionNumber(prompt, options.size())));
            }
        }
        return results;
    }

    /**
     * Подсказка к вопросу для вывода в консоль
     *
     * @param question вопрос
     * @return строка
     * @throws ConsolePromptBuildingError ошибка формирования подсказки
     */
    @NonNull
    private String getQuestionPrompt(Question question) throws ConsolePromptBuildingError {
        if (question == null) {
            throw new ConsolePromptBuildingError("Empty question");
        }
        if (!StringUtils.hasText(question.getText())) {
            throw new ConsolePromptBuildingError("Empty question text");
        }
        if (!StringUtils.hasText(question.getNumber())) {
            throw new ConsolePromptBuildingError("Question {0} has empty number", question.getText());
        }
        var sb = new StringBuilder(
                MessageFormat.format("{0}) {1}\n", question.getNumber(), question.getText()));
        if (!CollectionUtils.isEmpty(question.getOptions())
                && question.getOptions().stream().allMatch(StringUtils::hasText)) {
            for (var i = 0; i < question.getOptions().size(); i++) {
                sb.append(
                        MessageFormat.format("{0}) {1}\n", i + 1, question.getOptions().get(i)));
            }
            sb.append("Select ony one option: ");
        }
        return sb.toString();
    }

    /**
     * Ответ на вопрос в произвольной форме
     *
     * @param question вопрос
     * @return не-пустая строка
     */
    @NonNull
    private String getTextAnswer(String question) {
        System.out.print(question);
        var scanner = new Scanner(System.in);
        String input;
        do {
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                System.out.println("Answer is required");
                continue;
            }
            break;
        } while (true);
        return input.trim();
    }

    /**
     * Ответ на вопрос в форме выбранной опции
     *
     * @param question    вопрос
     * @param optionsSize количество опций
     * @return целое число в пределах количества опций
     */
    @NonNull
    private Integer getOptionNumber(String question, int optionsSize) {
        System.out.print(question);
        var scanner = new Scanner(System.in);
        Integer selected;
        do {
            String input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                System.out.println("Answer is required");
                continue;
            }
            try {
                selected = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Option number must be an integer");
                continue;
            }
            if (selected < 1 || selected > optionsSize) {
                System.out.printf("Option number must be in range of 1..%s%n", optionsSize);
                continue;
            }
            break;
        } while (true);
        return selected;
    }
}
