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
    @DisplayName("Методы count(), insert(), update(), getById(), getByFullname(), getAll(), deleteById()")
    void test() {
        final var counted = authorDao.count();
        assertEquals(EXPECTED_AUTHORS_COUNT, counted);
        final var insertedId = authorDao.insert(AUTHOR_MOCK);
        assertEquals(EXPECTED_INSERTED_AUTHOR_ID, insertedId);
        final var author = Author.builder()
                .id(EXPECTED_INSERTED_AUTHOR_ID)
                .fullName(JOHN_DOE)
                .build();
        assertDoesNotThrow(() -> authorDao.update(author));
        final var foundById = authorDao.getById(EXPECTED_INSERTED_AUTHOR_ID);
        assertEquals(JOHN_DOE, foundById.getFullName());
        final var foundByFullname = authorDao.getByFullname(JOHN_DOE);
        assertNotNull(foundByFullname);
        final var foundAll = authorDao.getAll();
        assertEquals(EXPECTED_INSERTED_AUTHOR_ID, foundAll.size());
        assertDoesNotThrow(() -> authorDao.deleteById(EXPECTED_INSERTED_AUTHOR_ID));
    }
}