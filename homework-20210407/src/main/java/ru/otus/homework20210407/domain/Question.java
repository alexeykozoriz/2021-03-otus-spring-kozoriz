package ru.otus.homework20210407.domain;

import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Вопрос
 */
@Data
public class Question {

    private final String number;
    private final String text;
    private final List<String> options;
    private final List<Integer> rightOptions;

    /**
     * Фабричный метод для маппинга вопроса из CSV
     *
     * @param csvRow строка CSV
     * @return вопрос
     */
    public static Question map(String[] csvRow) {
        if (csvRow == null || csvRow.length != 4) {
            return null;
        }
        List<String> options = csvRow[2] != null
                ? Arrays.stream(csvRow[2].split(",")).filter(StringUtils::hasText).map(String::trim).collect(Collectors.toList())
                : null;
        List<Integer> rightOptions = csvRow[3] != null
                ? Arrays.stream(csvRow[3].split(",")).filter(NumberUtils::isParsable).map(Integer::parseInt).collect(Collectors.toList())
                : null;
        return new Question(csvRow[0], csvRow[1], options, rightOptions);
    }
}
