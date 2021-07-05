package ru.otus.homework20210428.repository;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210428.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Реализация через JPA
 */
@Repository
public class BookCommentRepositoryJpaImpl implements BookCommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == null) {
            entityManager.persist(bookComment);
            return bookComment;
        } else {
            return entityManager.merge(bookComment);
        }
    }

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(entityManager.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> findAll() {
        return entityManager.createQuery("select bc from BookComment bc", BookComment.class).getResultList();
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
