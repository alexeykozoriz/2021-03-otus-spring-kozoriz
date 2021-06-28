package ru.otus.homework20210421.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210421.domain.Author;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework20210421.test.Constants.*;

@DisplayName("Объект для доступа к данным авторов")
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import(AuthorDaoJdbcImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorDaoJdbcImplTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @Order(1)
    @DisplayName("Метод count()")
    void count() {
        var counted = authorDao.count();
        assertEquals(EXPECTED_AUTHORS_COUNT, counted);
    }

    @Test
    @Order(2)
    @DisplayName("Метод insert()")
    void insert() {
        var insertedId = authorDao.insert(AUTHOR_MOCK);
        assertEquals(EXPECTED_INSERTED_AUTHOR_ID, insertedId);
    }

    @Test
    @Order(3)
    @DisplayName("Метод update()")
    void update() {
        var author = Author.builder()
                .id(EXPECTED_INSERTED_AUTHOR_ID)
                .fullName(JOHN_DOE)
                .build();
        assertDoesNotThrow(() -> authorDao.update(author));
    }

    @Test
    @Order(4)
    @DisplayName("Метод getById()")
    void getById() {
        var found = authorDao.getById(EXPECTED_INSERTED_AUTHOR_ID);
        assertEquals(JOHN_DOE, found.getFullName());
    }

    @Test
    @Order(5)
    @DisplayName("Метод getByFullname()")
    void getByFullname() {
        var found = authorDao.getByFullname(JOHN_DOE);
        assertNotNull(found);
    }

    @Test
    @Order(6)
    @DisplayName("Метод getAll()")
    void getAll() {
        var found = authorDao.getAll();
        assertEquals(EXPECTED_INSERTED_AUTHOR_ID, found.size());
    }

    @Test
    @Order(7)
    @DisplayName("Метод deleteById()")
    void deleteById() {
        assertDoesNotThrow(() -> authorDao.deleteById(EXPECTED_INSERTED_AUTHOR_ID));
    }
}