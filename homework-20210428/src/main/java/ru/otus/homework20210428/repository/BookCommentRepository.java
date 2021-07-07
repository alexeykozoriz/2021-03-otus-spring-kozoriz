package ru.otus.homework20210428.repository;

import ru.otus.homework20210428.domain.BookComment;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Интерфейс хранилища комментариев к книгам
 */
public interface BookCommentRepository {

    BookComment save(@NotNull BookComment bookComment);

    List<BookComment> findByBookId(long id);

    void deleteByBookId(long id);
}
