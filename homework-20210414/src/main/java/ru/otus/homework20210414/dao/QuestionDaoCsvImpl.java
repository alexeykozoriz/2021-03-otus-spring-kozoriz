package ru.otus.homework20210414.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210414.domain.Question;
import ru.otus.homework20210414.error.QuestionsReadingError;
import ru.otus.homework20210414.utils.QuestionMapper;

import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация хранилища вопросов в виде локализованного CSV-файла
 */
@Repository
public class QuestionDaoCsvImpl implements QuestionDao {

    private final String resourceName;
    private final QuestionMapper questionMapper;

    public QuestionDaoCsvImpl(@Value("${application.csv-resource-name}") String resourceName,
                              QuestionMapper questionMapper) {
        this.resourceName = resourceName;
        this.questionMapper = questionMapper;
    }

    @Override
    public List<Question> findAll(String locale) throws QuestionsReadingError {
        var result = new ArrayList<Question>();
        var resourceNameLocalized = MessageFormat.format(resourceName, locale);
        try (var csvReader = new CSVReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(resourceNameLocalized))))) {
            csvReader.readAll().stream().map(questionMapper::mapCsv).filter(Objects::nonNull).forEach(result::add);
        } catch (Exception e) {
            throw new QuestionsReadingError(e);
        }
        return result;
    }
}
