package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210407.domain.Answer;
import ru.otus.homework20210407.domain.AnswerByOption;
import ru.otus.homework20210407.domain.AnswerByText;
import ru.otus.homework20210407.domain.Question;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация по-умолчанию.
 * Если в вопросе нет опций, то запрашивается ответ в свободной форме.
 * Если в вопросе есть опции - запрашивается ответ в форме выбранной опции.
 */
@Service
@RequiredArgsConstructor
public class AnswersServiceImpl implements AnswersService {

    private final InteractionService interactionService;
    private final LocalizationService localizationService;

    @Override
    public List<Answer> getAnswers(List<Question> questions) {
        List<Answer> results = new ArrayList<>();
        for (Question question : questions) {
            final var prompt = MessageFormat.format("{0}) {1}: ", question.getNumber(), question.getText());
            final var options = question.getOptions();
            if (CollectionUtils.isEmpty(options)) {
                final var textAnswer = interactionService.readString(prompt);
                final var answerByText = new AnswerByText(question, textAnswer);
                results.add(answerByText);
            } else {
                interactionService.outputString(prompt);
                for (var i = 0; i < question.getOptions().size(); i++) {
                    final var optionText = MessageFormat.format("{0}) {1}", i + 1, question.getOptions().get(i));
                    interactionService.outputString(optionText);
                }
                final var optionPrompt = localizationService.getLocalizedString("option-prompt");
                final var optionNumber = interactionService.readIntByInterval(optionPrompt, options.size());
                final var answerByOption = new AnswerByOption(question, optionNumber);
                results.add(answerByOption);
            }
        }
        return results;
    }
}
