package ru.otus.homework20210517.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework20210517.domain.BookComment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework20210517.test.MockFactory.createBook;
import static ru.otus.homework20210517.test.MockFactory.createBookComment;

@DisplayName("Хранилище комментариев (JPA)")
@DataJpaTest
class BookCommentRepositoryTest {

    @Autowired
    private BookCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("Сохранение")
    @Test
    void save() {
        val expected = createBookComment();
        repositoryJpa.save(expected);
        assertThat(expected.getId()).isPositive();
        val actual = entityManager.find(BookComment.class, expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("Поиск по идентификатору книги")
    @Test
    void findById() {
        val expectedBook = createBook();
        entityManager.persist(expectedBook);
        val expected = createBookComment();
        expected.setBook(expectedBook);
        entityManager.persist(expected);
        val actual = (List<BookComment>) repositoryJpa.findByBookId(expected.getBook().getId());
        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("Удаление")
    @Test
    void deleteById() {
        val expectedBook = createBook();
        entityManager.persist(expectedBook);
        val expected = createBookComment();
        expected.setBook(expectedBook);
        entityManager.persist(expected);
        repositoryJpa.deleteByBookId(expectedBook.getId());
        val actual = entityManager.persistFlushFind(expected);
        assertThat(actual).isNull();
    }
}