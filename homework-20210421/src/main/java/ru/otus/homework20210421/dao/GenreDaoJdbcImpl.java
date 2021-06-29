package ru.otus.homework20210421.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210421.domain.Genre;
import ru.otus.homework20210421.util.GenreMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbcImpl implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int count() {
        final var count = namedParameterJdbcOperations.getJdbcOperations().queryForObject(
                "select count(id) from genres", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Genre genre) {
        final var params = new MapSqlParameterSource();
        params.addValue("title", genre.getTitle());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(
                "insert into genres (title) values (:title)",
                params,
                keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void update(Genre genre) {
        final Map<String, Object> params = Map.of(
                "id", genre.getId(),
                "title", genre.getTitle());
        namedParameterJdbcOperations.update(
                "update genres set title = :title where id = :id",
                params);
    }

    @Override
    public Genre getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, title from genres where id = :id", params, new GenreMapper());
    }

    @Override
    public List<Genre> getByTitle(String pattern) {
        final Map<String, Object> params = Map.of("title", pattern);
        return namedParameterJdbcOperations.query(
                "select id, title from genres where title like :title", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select id, title from genres", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations.update(
                "delete from genres where id = :id",
                params);
    }
}
