package ru.otus.homework20210421.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework20210421.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework20210421.test.Constants.*;

@DisplayName("Объект для доступа к данным жанров")
@JdbcTest
@Import(GenreDaoJdbcImpl.class)
class GenreDaoJdbcImplTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("Метод count()")
    void count() {
        var counted = genreDao.count();
        assertEquals(EXPECTED_GENRES_COUNT, counted);
    }

    @Test
    @DisplayName("Метод insert()")
    void insert() {
        var insertedId = genreDao.insert(GENRE_MOCK);
        assertEquals(EXPECTED_INSERTED_GENRE_ID, insertedId);
    }

    @Test
    @DisplayName("Метод update()")
    void update() {
        var genre = Genre.builder()
                .id(EXPECTED_INSERTED_GENRE_ID)
                .title(TESTING_FICTON)
                .build();
        assertDoesNotThrow(() -> genreDao.update(genre));
    }

    @Test
    @DisplayName("Метод getById()")
    void getById() {
        var found = genreDao.getById(EXPECTED_GENRES_COUNT);
        assertEquals(TESTING_FICTON, found.getTitle());
    }

    @Test
    @DisplayName("Метод getByFullname()")
    void getByFullname() {
        var found = genreDao.getByTitle(TESTING_FICTON);
        assertNotNull(found);
    }

    @Test
    @DisplayName("Метод getAll()")
    void getAll() {
        var found = genreDao.getAll();
        assertEquals(EXPECTED_GENRES_COUNT, found.size());
    }

    @Test
    @DisplayName("Метод deleteById()")
    void deleteById() {
        assertDoesNotThrow(() -> genreDao.deleteById(EXPECTED_INSERTED_AUTHOR_ID));
    }
}