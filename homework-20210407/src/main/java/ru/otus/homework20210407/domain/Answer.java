package ru.otus.homework20210407.domain;

import lombok.Data;

/**
 * Ответ на вопрос с оценкой
 */
@Data
public class Answer {
    public final Question question;
    public final Integer selectedOption;
}
