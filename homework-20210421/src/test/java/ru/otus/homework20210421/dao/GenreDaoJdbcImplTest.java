package ru.otus.homework20210421.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210421.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework20210421.test.Constants.*;

@DisplayName("Объект для доступа к данным жанров")
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import(GenreDaoJdbcImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreDaoJdbcImplTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @Order(1)
    @DisplayName("Метод count()")
    void count() {
        var counted = genreDao.count();
        assertEquals(EXPECTED_GENRES_COUNT, counted);
    }

    @Test
    @Order(2)
    @DisplayName("Метод insert()")
    void insert() {
        var insertedId = genreDao.insert(GENRE_MOCK);
        assertEquals(EXPECTED_INSERTED_GENRE_ID, insertedId);
    }

    @Test
    @Order(3)
    @DisplayName("Метод update()")
    void update() {
        var genre = Genre.builder()
                .id(EXPECTED_INSERTED_GENRE_ID)
                .title(TESTING_FICTON)
                .build();
        assertDoesNotThrow(() -> genreDao.update(genre));
    }

    @Test
    @Order(4)
    @DisplayName("Метод getById()")
    void getById() {
        var found = genreDao.getById(EXPECTED_INSERTED_GENRE_ID);
        assertEquals(TESTING_FICTON, found.getTitle());
    }

    @Test
    @Order(5)
    @DisplayName("Метод getByFullname()")
    void getByFullname() {
        var found = genreDao.getByTitle(TESTING_FICTON);
        assertNotNull(found);
    }

    @Test
    @Order(6)
    @DisplayName("Метод getAll()")
    void getAll() {
        var found = genreDao.getAll();
        assertEquals(EXPECTED_INSERTED_GENRE_ID, found.size());
    }

    @Test
    @Order(7)
    @DisplayName("Метод deleteById()")
    void deleteById() {
        assertDoesNotThrow(() -> genreDao.deleteById(EXPECTED_INSERTED_AUTHOR_ID));
    }
}