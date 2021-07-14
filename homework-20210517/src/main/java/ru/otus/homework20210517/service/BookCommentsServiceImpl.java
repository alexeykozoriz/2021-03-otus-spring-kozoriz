package ru.otus.homework20210517.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210517.domain.BookComment;
import ru.otus.homework20210517.repository.BookCommentRepository;

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
        return commentRepository.findAllByBook_Id(bookId);
    }

    @Transactional
    @Override
    public void deleteByBookId(long bookId) {
        commentRepository.deleteAllByBook_Id(bookId);
    }
}
