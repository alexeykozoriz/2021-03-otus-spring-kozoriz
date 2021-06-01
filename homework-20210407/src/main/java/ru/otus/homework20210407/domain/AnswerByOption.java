package ru.otus.homework20210407.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Ответ через выбор номера опции
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class AnswerByOption extends Answer {
    private final Integer selectedOption;

    public AnswerByOption(Question question, Integer optionNumber) {
        this.question = question;
        this.selectedOption = optionNumber;
    }
}
