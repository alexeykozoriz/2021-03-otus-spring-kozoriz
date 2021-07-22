package ru.otus.homework20210607.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework20210607.domain.Book;
import ru.otus.homework20210607.repository.AuthorRepository;
import ru.otus.homework20210607.repository.BookRepository;
import ru.otus.homework20210607.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация по-умолчанию
 * Авторы и жанры создаются при создании и редактировании книги.
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public void save(Book book) {
        val author = book.getAuthor();
        if (author != null && author.getId() == null) {
            authorRepository.findByFullNameEquals(author.getFullName()).ifPresent(book::setAuthor);
        }
        val genre = book.getGenre();
        if (genre != null && genre.getId() == null) {
            genreRepository.findByTitleEquals(genre.getTitle()).ifPresent(book::setGenre);
        }
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> read(long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> read() {
        return bookRepository.findAll();
    }
}
