package ru.otus.homework20210428.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework20210428.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Реализация через JPA
 */
@Repository
public class BookRepositoryJpaImpl implements BookRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        final var query = entityManager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
