package ru.otus.homework20210517.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework20210517.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.homework20210517.test.MockFactory.createGenre;

@DisplayName("Хранилище жанров (JPA)")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository repositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("Сохранение")
    @Test
    void save() {
        val expected = createGenre();
        repositoryJpa.save(expected);
        assertThat(expected.getId()).isPositive();
        val actual = entityManager.find(Genre.class, expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("Поиск по идентификатору")
    @Test
    void findById() {
        val expected = createGenre();
        entityManager.persist(expected);
        val actual = repositoryJpa.findById(expected.getId());
        assertThat(actual).isPresent().get().usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("Поиск по названию")
    @Test
    void findByFullName() {
        val expected = createGenre();
        entityManager.persist(expected);
        val actual = repositoryJpa.findByTitle(expected.getTitle());
        assertThat(actual).isPresent().get().usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("Поиск всех")
    @Test
    void findAll() {
        val expected = createGenre();
        entityManager.persist(expected);
        val actuals = (List<Genre>) repositoryJpa.findAll();
        assertThat(actuals.size()).isEqualTo(5);
    }

    @DisplayName("Удаление")
    @Test
    void deleteById() {
        val expected = createGenre();
        entityManager.persist(expected);
        repositoryJpa.deleteById(expected.getId());
        val actual = entityManager.find(Genre.class, expected.getId());
        assertThat(actual).isNull();
    }
}