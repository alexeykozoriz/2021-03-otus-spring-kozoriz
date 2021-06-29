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
    @DisplayName("Методы count(), insert(), update(), getById(), getAll(), deleteById()")
    void test() {
        final var counted = bookDao.count();
        assertEquals(EXPECTED_BOOKS_COUNT, counted);
        final var insertedId = bookDao.insert(BOOK_MOCK_WITH_IDS_2);
        assertEquals(EXPECTED_INSERTED_BOOK_ID, insertedId);
        final var book = Book.builder()
                .id(EXPECTED_INSERTED_BOOK_ID)
                .title(THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG)
                .publicationYear(BOOK_MOCK.getPublicationYear())
                .author(BOOK_MOCK_WITH_IDS_2.getAuthor())
                .genre(BOOK_MOCK_WITH_IDS_2.getGenre())
                .build();
        assertDoesNotThrow(() -> bookDao.update(book));
        final var foundById = bookDao.getById(EXPECTED_INSERTED_BOOK_ID);
        assertEquals(THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG, foundById.getTitle());
        final var foundAll = bookDao.getAll();
        assertEquals(EXPECTED_INSERTED_BOOK_ID, foundAll.size());
        assertDoesNotThrow(() -> bookDao.deleteById(EXPECTED_INSERTED_BOOK_ID));
    }
}