package ru.otus.homework20210428.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210428.domain.BookComment;
import ru.otus.homework20210428.repository.BookCommentRepository;

import java.util.List;

/**
 * Реализация по-умолчанию
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

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> findByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Transactional
    @Override
    public void deleteByBookId(long bookId) {
        commentRepository.deleteByBookId(bookId);
    }
}
