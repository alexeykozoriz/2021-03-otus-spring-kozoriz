package ru.otus.homework20210428.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210428.domain.BookComment;
import ru.otus.homework20210428.repository.BookCommentRepository;

import javax.transaction.Transactional;

/**
 * Реализация по-умолчанию
 * Комментарии не удаляются.
 */
@Service
@RequiredArgsConstructor
public class BookCommentsServiceImpl implements BookCommentsService {

    private final BookCommentRepository commentRepository;

    @Transactional
    @Override
    public void save(BookComment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void delete(long id) {
        commentRepository.deleteById(id);
    }
}
