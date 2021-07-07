package ru.otus.homework20210428.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework20210428.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Реализация через JPA
 */
@Repository
public class GenreRepositoryJpaImpl implements GenreRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> findByTitle(String title) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where g.title like :title", Genre.class);
        query.setParameter("title", title);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Genre> findAll() {
        return entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        final var query = entityManager.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
