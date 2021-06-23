package ru.otus.homework20210414.domain;

import lombok.Getter;

/**
 * Ответ
 */
@Getter
public abstract class Answer {
    protected Question question;
}
