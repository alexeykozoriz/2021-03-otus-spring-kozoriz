package ru.otus.homework20210428.repository;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework20210428.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return entityManager.createQuery("select b from Book b", Book.class).getResultList();
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
