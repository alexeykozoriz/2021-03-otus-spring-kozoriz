package ru.otus.homework20210428.repository;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210428.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Реализация через JPA
 */
@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public Optional<Author> findByFullName(String fullName) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.fullName like :fullName", Author.class);
        query.setParameter("fullName", fullName);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Author> findAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        val found = findById(id);
        if (found.isEmpty()) {
            return;
        }
        entityManager.remove(found.get());
    }
}
