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
import ru.otus.homework20210407.error.PromptBuildingError;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация по-умолчанию.
 * Если в вопросе нет опций, то запрашивается ответ в свободной форме.
 * Если в вопросе есть опции - запрашивается ответ в форме выбранной опции.
 * Подсказка к вопросу собирается из номера и текста вопроса.
 * Если в вопросе отсутствуют текст и номер, то кидается ошибка.
 */
@Service
@RequiredArgsConstructor
public class AnswersServiceImpl implements AnswersService {

    private final InteractionService interactionService;

    @Override
    public List<Answer> getAnswers(List<Question> questions) throws PromptBuildingError {
        List<Answer> results = new ArrayList<>();
        for (Question question : questions) {
            final var options = question.getOptions();
            final String prompt = getQuestionPrompt(question);
            if (CollectionUtils.isEmpty(options)) {
                results.add(new AnswerByText(question, interactionService.requestTextAnswer(prompt)));
            } else {
                results.add(new AnswerByOption(question, interactionService.requestOptionNumber(prompt, options.size())));
            }
        }
        return results;
    }

    /**
     * Подсказка к вопросу для вывода в консоль
     *
     * @param question вопрос
     * @return строка
     * @throws PromptBuildingError ошибка создания подсказки
     */
    @NonNull
    private String getQuestionPrompt(Question question) throws PromptBuildingError {
        if (question == null) {
            throw new PromptBuildingError("Empty question");
        }
        if (!StringUtils.hasText(question.getText())) {
            throw new PromptBuildingError("Empty question text");
        }
        if (!StringUtils.hasText(question.getNumber())) {
            throw new PromptBuildingError("Question {0} has empty number", question.getText());
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
}