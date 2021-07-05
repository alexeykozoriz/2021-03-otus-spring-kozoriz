package ru.otus.homework20210428.repository;

import ru.otus.homework20210428.domain.BookComment;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища комментариев к книгам
 */
public interface BookCommentRepository {

    BookComment save(@NotNull BookComment bookComment);

    Optional<BookComment> findById(long id);

    List<BookComment> findAll();

    void deleteById(long id);
}
