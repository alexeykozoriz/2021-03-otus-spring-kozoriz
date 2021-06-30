package ru.otus.homework20210421.util;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework20210421.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Утилитный класс для маппинга книг
 */
public class GenreMapper implements RowMapper<Genre> {

    /**
     * Маппинг из результатов БД
     */
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .build();
    }
}
