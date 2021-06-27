package ru.otus.homework20210421.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210421.dao.BookDao;
import ru.otus.homework20210421.domain.Author;
import ru.otus.homework20210421.domain.Book;
import ru.otus.homework20210421.domain.Genre;

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

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public long create(Book book) {
        joinAuthor(book.getAuthor());
        joinGenre(book.getGenre());
        return bookDao.insert(book);
    }

    @Override
    public void delete(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Optional<Book> read(long id) {
        return Optional.of(bookDao.getById(id));
    }

    @Override
    public List<Book> read() {
        return bookDao.getAll();
    }

    @Override
    public void update(Book book) {
        joinAuthor(book.getAuthor());
        joinGenre(book.getGenre());
        bookDao.update(book);
    }

    /**
     * Создание или поиск подходящего жанра
     *
     * @param genre жанр
     */
    private void joinGenre(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Genre required");
        }
        if (genre.getId() != null) {
            return;
        }
        var found = genreService.read(genre.getTitle());
        if (CollectionUtils.isEmpty(found)) {
            genre.setId(genreService.create(genre));
        } else {
            genre.setId(found.get(0).getId());
        }
    }

    /**
     * Создание или поиск подходящего автора
     *
     * @param author автор
     */
    private void joinAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author required");
        }
        if (author.getId() != null) {
            return;
        }
        var found = authorService.read(author.getFullName());
        if (CollectionUtils.isEmpty(found)) {
            author.setId(authorService.create(author));
        } else {
            author.setId(found.get(0).getId());
        }
    }
}
