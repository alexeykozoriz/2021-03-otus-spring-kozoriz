package ru.otus.homework20210421.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210421.domain.Author;
import ru.otus.homework20210421.util.AuthorMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbcImpl implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        final var count = namedParameterJdbcOperations.getJdbcOperations().queryForObject(
                "select count(id) from authors", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Author author) {
        final var params = new MapSqlParameterSource();
        params.addValue("full_name", author.getFullName());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(
                "insert into authors (full_name) values (:full_name)",
                params,
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void update(Author author) {
        final Map<String, Object> params = Map.of(
                "id", author.getId(),
                "full_name", author.getFullName());
        namedParameterJdbcOperations.update(
                "update authors set full_name = :full_name where id = :id",
                params);
    }

    @Override
    public Author getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, full_name from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public List<Author> getByFullname(String pattern) {
        final Map<String, Object> params = Map.of("full_name", pattern);
        return namedParameterJdbcOperations.query(
                "select id, full_name from authors where full_name like :full_name", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select * from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id",
                params);
    }
}
