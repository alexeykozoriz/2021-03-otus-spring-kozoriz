package ru.otus.homework20210707.producer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.homework20210707.producer.domain.BookComment;

/**
 * Интерфейс хранилища комментариев к книгам
 */
@RepositoryRestResource(path = "comments")
public interface BookCommentRepository extends PagingAndSortingRepository<BookComment, Long> {

    @RestResource(rel = "find-by-book-id", path = "book")
    Page<BookComment> findAllByBook_Id(@Param("id") Long id, Pageable page);

}
