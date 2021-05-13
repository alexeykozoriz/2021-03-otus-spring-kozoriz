package ru.otus.kozoriz.homework20210324.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Вопрос
 */
public class Question {

    private final String number;
    private final String text;
    private final List<String> options;

    public Question(String number, String text, List<String> options) {
        this.number = number;
        this.text = text;
        this.options = options;
    }

    /**
     * Фабричный метод для маппинга вопроса из CSV
     *
     * @param csvRow строка CSV
     * @return вопрос
     */
    public static Question map(String[] csvRow) {
        if (csvRow == null || csvRow.length != 3) {
            return null;
        }
        List<String> options = csvRow[2] != null
                ? Arrays.stream(csvRow[2].split(",")).map(String::trim).collect(Collectors.toList())
                : null;
        return new Question(csvRow[0], csvRow[1], options);
    }

    public String getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }
}
