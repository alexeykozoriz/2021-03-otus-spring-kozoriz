package ru.otus.homework20210421.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210421.domain.Book;
import ru.otus.homework20210421.util.BookMapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Реализация через Jdbc
 */
@Repository
@RequiredArgsConstructor
public class BookDaoJdbcImpl implements BookDao {
    private final JdbcOperations jdbcOperations;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        final var count = jdbcOperations.queryForObject(
                "select count(*) from books", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Book book) {
        final var params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("publication_year", book.getPublicationYear());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(
                "insert into books (title, publication_year, author_id, genre_id) values (:title, :publication_year, :author_id, :genre_id)",
                params,
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void update(Book book) {
        final Map<String, ? extends Serializable> params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "publication_year", book.getPublicationYear(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId());
        namedParameterJdbcOperations.update(
                "update books set title = :title, publication_year = :publication_year, author_id = :author_id, genre_id = :genre_id where id = :id",
                params);
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select b.*, a.full_name as author, g.title as genre from books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id where b.id = :id", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query(
                "select b.*, a.full_name as author, g.title as genre from books b join authors a on a.id = b.author_id join genres g on g.id = b.genre_id",
                new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id",
                params);
    }
}
