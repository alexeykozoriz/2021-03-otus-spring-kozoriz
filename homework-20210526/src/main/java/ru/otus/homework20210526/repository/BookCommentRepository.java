package ru.otus.homework20210526.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import ru.otus.homework20210526.domain.BookComment;

import java.util.List;

/**
 * Интерфейс хранилища комментариев к книгам
 */
public interface BookCommentRepository extends CrudRepository<BookComment, Long> {

    List<BookComment> findAllByBook_Id(Long id);

    @Modifying
    List<BookComment> deleteAllByBook_Id(Long id);
}
