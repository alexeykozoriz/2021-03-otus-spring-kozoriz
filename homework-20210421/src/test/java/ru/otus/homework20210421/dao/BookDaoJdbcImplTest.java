package ru.otus.homework20210421.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework20210421.domain.Book;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.homework20210421.test.Constants.*;

@DisplayName("Объект для доступа к данным книг")
@JdbcTest
@Import(BookDaoJdbcImpl.class)
class BookDaoJdbcImplTest {

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("Метод count()")
    void count() {
        var counted = bookDao.count();
        assertEquals(EXPECTED_BOOKS_COUNT, counted);
    }

    @Test
    @DisplayName("Метод insert()")
    void insert() {
        var insertedId = bookDao.insert(BOOK_MOCK_WITH_IDS_2);
        assertEquals(EXPECTED_INSERTED_BOOK_ID, insertedId);
    }

    @Test
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
    @DisplayName("Метод getById()")
    void getById() {
        var found = bookDao.getById(EXPECTED_BOOKS_COUNT);
        assertEquals(THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG, found.getTitle());
    }

    @Test
    @DisplayName("Метод getAll()")
    void getAll() {
        var found = bookDao.getAll();
        assertEquals(EXPECTED_BOOKS_COUNT, found.size());
    }

    @Test
    @DisplayName("Метод deleteById()")
    void deleteById() {
        assertDoesNotThrow(() -> bookDao.deleteById(EXPECTED_INSERTED_BOOK_ID));
    }
}