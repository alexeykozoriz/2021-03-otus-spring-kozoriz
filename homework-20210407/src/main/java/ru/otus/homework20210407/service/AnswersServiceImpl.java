package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.AnswerByOption;
import ru.otus.homework20210407.domain.AnswerByText;
import ru.otus.homework20210407.domain.Question;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Реализация по-умолчанию.
 * Если в вопросе нет опций, то запрашивается ответ в свободной форме.
 * Если в вопросе есть опции - запрашивается ответ в форме выбранной опции.
 */
@Service
@RequiredArgsConstructor
public class AnswersServiceImpl implements AnswersService {

    private final InteractionService interactionService;
    private final MessageSource messageSource;

    @Override
    public List<Answer> getAnswers(List<Question> questions) {
        List<Answer> results = new ArrayList<>();
        for (Question question : questions) {
            final var questionPrompt = MessageFormat.format("{0}) {1}: ", question.getNumber(), question.getText());
            final var options = question.getOptions();
            if (CollectionUtils.isEmpty(options)) {
                results.add(
                        new AnswerByText(question,
                                interactionService.readString(questionPrompt)));
            } else {
                interactionService.outputString(questionPrompt);
                for (var i = 0; i < question.getOptions().size(); i++) {
                    interactionService.outputString(
                            MessageFormat.format("{0}) {1}", i + 1, question.getOptions().get(i)));
                }
                results.add(
                        new AnswerByOption(question,
                                interactionService.readIntByInterval(
                                        messageSource.getMessage("option-prompt", null, Locale.forLanguageTag("ru-RU")), options.size())));
            }
        }
        return results;
    }
}
