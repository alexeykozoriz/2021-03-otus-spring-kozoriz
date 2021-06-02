package ru.otus.homework20210407.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework20210407.domain.Question;
import ru.otus.homework20210407.error.CsvReadError;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация хранилища вопрсов в виде CSV-файла
 */
@Component
public class QuestionDaoCsvImpl implements QuestionDao {

    @Value("${application.csv-resource-name}")
    private String resourceName;

    @Override
    public List<Question> findAll() throws CsvReadError {
        var result = new ArrayList<Question>();
        try (var csvReader = new CSVReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(resourceName))))) {
            csvReader.readAll().stream().map(Question::map).filter(Objects::nonNull).forEach(result::add);
        } catch (Exception e) {
            throw new CsvReadError(e);
        }
        return result;
    }
}
