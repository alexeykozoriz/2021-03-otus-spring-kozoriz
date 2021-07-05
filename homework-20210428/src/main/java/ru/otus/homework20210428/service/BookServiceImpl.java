package ru.otus.homework20210428.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.homework20210428.domain.Book;
import ru.otus.homework20210428.repository.AuthorRepository;
import ru.otus.homework20210428.repository.BookRepository;
import ru.otus.homework20210428.repository.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Реализация по-умолчанию
 * Авторы и жанры создаются при создании и редактировании книги.
 * Авторы и жанры не удаляются.
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
            authorRepository.findByFullName(author.getFullName()).ifPresent(book::setAuthor);
        }
        val genre = book.getGenre();
        if (genre != null && genre.getId() == null) {
            genreRepository.findByTitle(genre.getTitle()).ifPresent(book::setGenre);
        }
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Optional<Book> read(long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public List<Book> read() {
        return bookRepository.findAll();
    }
}
