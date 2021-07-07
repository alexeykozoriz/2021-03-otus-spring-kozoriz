package ru.otus.homework20210428.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework20210428.domain.BookComment;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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
    public List<BookComment> findByBookId(long bookId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-comment-entity-graph");
        TypedQuery<BookComment> query = entityManager.createQuery("select bc from BookComment bc where bc.book.id = :bookId", BookComment.class);
        query.setParameter("bookId", bookId);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteByBookId(long bookId) {
        final var query = entityManager.createQuery("delete from BookComment bc where bc.book.id = :bookId");
        query.setParameter("bookId", bookId);
        query.executeUpdate();
    }
}
