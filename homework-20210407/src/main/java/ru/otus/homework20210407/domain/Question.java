package ru.otus.homework20210407.domain;

import lombok.Data;

import java.util.List;

/**
 * Вопрос
 */
@Data
public class Question {

    private final String number;
    private final String text;
    private final List<String> options;
    private final List<Integer> rightOptions;

}
