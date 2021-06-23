package ru.otus.homework20210414.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Ответ текстом
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class AnswerByText extends Answer {
    private final String text;

    public AnswerByText(Question question, String textAnswer) {
        this.question = question;
        this.text = textAnswer;
    }
}
