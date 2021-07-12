package ru.otus.homework20210517.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework20210517.domain.BookComment;

/**
 * Интерфейс хранилища комментариев к книгам
 */
public interface BookCommentRepository extends CrudRepository<BookComment, Long> {

    @Query("select bc from BookComment bc where bc.book.id = :id")
    Iterable<BookComment> findByBookId(@Param("id") Long id);

    @Modifying
    @Query("delete from BookComment bc where bc.book.id = :id")
    void deleteByBookId(@Param("id") Long id);
}
