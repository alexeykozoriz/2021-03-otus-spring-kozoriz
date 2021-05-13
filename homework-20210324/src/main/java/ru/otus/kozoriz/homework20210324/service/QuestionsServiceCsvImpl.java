package ru.otus.kozoriz.homework20210324.service;

import com.opencsv.CSVReader;
import ru.otus.kozoriz.homework20210324.domain.Question;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация для работы с вопросами в формате CSV
 */
public class QuestionsServiceCsvImpl implements QuestionsService {
    private String resourceName;

    @Override
    public List<Question> findAllQuestions() {
        var result = new ArrayList<Question>();
        try (var csvReader = new CSVReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(resourceName))))) {
            csvReader.readAll().stream().map(Question::map).filter(Objects::nonNull).forEach(result::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
