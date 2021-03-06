package ru.otus.homework20210407.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.error.QuestionsReadingError;
import ru.otus.homework20210407.utils.QuestionMapper;

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
                              @Value("${application.locale}") String resourceLocale,
                              QuestionMapper questionMapper) {
        this.resourceName = MessageFormat.format(resourceName, resourceLocale);
        this.questionMapper = questionMapper;
    }

    @Override
    public List<Question> findAll() throws QuestionsReadingError {
        var result = new ArrayList<Question>();
        try (var csvReader = new CSVReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(resourceName))))) {
            csvReader.readAll().stream().map(questionMapper::mapCsv).filter(Objects::nonNull).forEach(result::add);
        } catch (Exception e) {
            throw new QuestionsReadingError(e);
        }
        return result;
    }
}
