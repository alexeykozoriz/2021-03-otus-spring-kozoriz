package ru.otus.homework20210407.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework20210407.domain.Question;

import java.io.IOException;
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
    public List<Question> findAll() throws IOException, CsvException {
        var result = new ArrayList<Question>();
        var csvReader = new CSVReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(resourceName), "CSV-resource not found")));
        csvReader.readAll().stream().map(Question::map).filter(Objects::nonNull).forEach(result::add);
        csvReader.close();
        return result;
    }
}
