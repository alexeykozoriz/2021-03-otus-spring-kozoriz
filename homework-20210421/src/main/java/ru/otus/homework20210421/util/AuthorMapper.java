package ru.otus.homework20210421.util;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework20210421.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Утилитный класс для маппинга авторов
 */
public class AuthorMapper implements RowMapper<Author> {

    /**
     * Маппинг из результатов БД
     */
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        return Author.builder()
                .id(resultSet.getLong("id"))
                .fullName(resultSet.getString("full_name"))
                .build();
    }
}
