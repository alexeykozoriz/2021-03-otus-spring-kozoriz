package ru.otus.homework20210428.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework20210428.domain.BookComment;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework20210428.test.MockFactory.createBookComment;

@DisplayName("Хранилище комментариев (JPA)")
@DataJpaTest
@Import(BookCommentRepositoryJpaImpl.class)
class BookCommentRepositoryJpaImplTest {

    @Autowired
    private BookCommentRepositoryJpaImpl repositoryJpa;

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

    @DisplayName("Поиск по идентификатору")
    @Test
    void findById() {
        val expected = createBookComment();
        entityManager.persist(expected);
        val actual = repositoryJpa.findById(expected.getId());
        assertThat(actual).isPresent().get().usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("Поиск всех")
    @Test
    void findAll() {
        val expected = createBookComment();
        entityManager.persist(expected);
        val actuals = repositoryJpa.findAll();
        assertThat(actuals.size()).isEqualTo(1);
    }

    @DisplayName("Удаление")
    @Test
    void deleteById() {
        val expected = createBookComment();
        entityManager.persist(expected);
        repositoryJpa.deleteById(expected.getId());
        val actual = entityManager.find(BookComment.class, expected.getId());
        assertThat(actual).isNull();
    }
}