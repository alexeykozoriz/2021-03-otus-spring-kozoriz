package ru.otus.homework20210421.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210421.domain.Book;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.homework20210421.test.Constants.*;

@DisplayName("Объект для доступа к данным книг")
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import(BookDaoJdbcImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookDaoJdbcImplTest {

    @Autowired
    private BookDao bookDao;

    @Test
    @Order(1)
    @DisplayName("Метод count()")
    void count() {
        var counted = bookDao.count();
        assertEquals(EXPECTED_BOOKS_COUNT, counted);
    }

    @Test
    @Order(2)
    @DisplayName("Метод insert()")
    void insert() {
        var insertedId = bookDao.insert(BOOK_MOCK_WITH_IDS);
        assertEquals(EXPECTED_INSERTED_BOOK_ID, insertedId);
    }

    @Test
    @Order(3)
    @DisplayName("Метод update()")
    void update() {
        var book = Book.builder()
                .id(EXPECTED_INSERTED_BOOK_ID)
                .title(THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG)
                .publicationYear(BOOK_MOCK.getPublicationYear())
                .author(BOOK_MOCK_WITH_IDS.getAuthor())
                .genre(BOOK_MOCK_WITH_IDS.getGenre())
                .build();
        assertDoesNotThrow(() -> bookDao.update(book));
    }

    @Test
    @Order(4)
    @DisplayName("Метод getById()")
    void getById() {
        var found = bookDao.getById(EXPECTED_INSERTED_BOOK_ID);
        assertEquals(THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG, found.getTitle());
    }

    @Test
    @Order(5)
    @DisplayName("Метод getAll()")
    void getAll() {
        var found = bookDao.getAll();
        assertEquals(EXPECTED_INSERTED_BOOK_ID, found.size());
    }

    @Test
    @Order(6)
    @DisplayName("Метод deleteById()")
    void deleteById() {
        assertDoesNotThrow(() -> bookDao.deleteById(EXPECTED_INSERTED_BOOK_ID));
    }
}