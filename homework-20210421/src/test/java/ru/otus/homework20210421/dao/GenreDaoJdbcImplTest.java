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
    @DisplayName("Методы count(), insert(), update(), getById(), getByFullname(), getAll(), deleteById()")
    void test() {
        final var counted = genreDao.count();
        assertEquals(EXPECTED_GENRES_COUNT, counted);
        final var insertedId = genreDao.insert(GENRE_MOCK);
        assertEquals(EXPECTED_INSERTED_GENRE_ID, insertedId);
        final var genre = Genre.builder()
                .id(insertedId)
                .title(TESTING_FICTON)
                .build();
        assertDoesNotThrow(() -> genreDao.update(genre));
        final var foundById = genreDao.getById(insertedId);
        assertEquals(TESTING_FICTON, foundById.getTitle());
        final var foundByTitle = genreDao.getByTitle(TESTING_FICTON);
        assertNotNull(foundByTitle);
        final var foundAll = genreDao.getAll();
        assertEquals(EXPECTED_INSERTED_GENRE_ID, foundAll.size());
        assertDoesNotThrow(() -> genreDao.deleteById(EXPECTED_INSERTED_AUTHOR_ID));
    }
}