package ru.otus.homework20210421.util;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework20210421.domain.Author;
import ru.otus.homework20210421.domain.Book;
import ru.otus.homework20210421.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Утилитный класс для маппинга книг
 */
public class BookMapper implements RowMapper<Book> {

    /**
     * Маппинг из результатов БД
     */
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        return Book.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .publicationYear(resultSet.getInt("publication_year"))
                .author(Author.builder()
                        .id(resultSet.getLong("author_id"))
                        .fullName(resultSet.getString("author"))
                        .build())
                .genre(Genre.builder()
                        .id(resultSet.getLong("genre_id"))
                        .title(resultSet.getString("genre"))
                        .build())
                .build();
    }
}
