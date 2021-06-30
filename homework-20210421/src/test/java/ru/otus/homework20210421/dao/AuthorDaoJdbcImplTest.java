package ru.otus.homework20210421.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework20210421.domain.Author;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework20210421.test.Constants.*;

@DisplayName("Объект для доступа к данным авторов")
@JdbcTest
@Import(AuthorDaoJdbcImpl.class)
class AuthorDaoJdbcImplTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Метод count()")
    void count() {
        var counted = authorDao.count();
        assertEquals(EXPECTED_AUTHORS_COUNT, counted);
    }

    @Test
    @DisplayName("Метод insert()")
    void insert() {
        var insertedId = authorDao.insert(AUTHOR_MOCK);
        assertEquals(EXPECTED_INSERTED_AUTHOR_ID, insertedId);
    }

    @Test
    @DisplayName("Метод update()")
    void update() {
        var author = Author.builder()
                .id(EXPECTED_INSERTED_AUTHOR_ID)
                .fullName(JANE_DOE)
                .build();
        assertDoesNotThrow(() -> authorDao.update(author));
    }

    @Test
    @DisplayName("Метод getById()")
    void getById() {
        var found = authorDao.getById(EXPECTED_AUTHORS_COUNT);
        assertEquals(JANE_DOE, found.getFullName());
    }

    @Test
    @DisplayName("Метод getByFullname()")
    void getByFullname() {
        var found = authorDao.getByFullname(JANE_DOE);
        assertNotNull(found);
    }

    @Test
    @DisplayName("Метод getAll()")
    void getAll() {
        var found = authorDao.getAll();
        assertEquals(EXPECTED_AUTHORS_COUNT, found.size());
    }

    @Test
    @DisplayName("Метод deleteById()")
    void deleteById() {
        assertDoesNotThrow(() -> authorDao.deleteById(EXPECTED_INSERTED_AUTHOR_ID));
    }
}