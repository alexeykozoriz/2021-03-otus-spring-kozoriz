package ru.otus.homework20210407.domain;

import lombok.Getter;

/**
 * Ответ
 */
@Getter
public abstract class Answer {
    protected Question question;
}
