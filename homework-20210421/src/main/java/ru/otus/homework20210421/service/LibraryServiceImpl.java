package ru.otus.homework20210421.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210421.domain.Author;
import ru.otus.homework20210421.domain.Book;
import ru.otus.homework20210421.domain.Genre;

import java.text.MessageFormat;
import java.time.LocalDate;

/**
 * Реализация по-умолчанию.
 */
@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final InteractionService interactionService;

    @Override
    public void addBook() {
        final var title = interactionService.readString("Title: ");
        final var publicationYear = interactionService.readNumberByInterval("Publication year: ", 1800, LocalDate.now().getYear());
        final var fullName = interactionService.readString("Author full name: ");
        final var genre = interactionService.readString("Genre: ");
        final long id = bookService.create(Book.builder()
                .title(title)
                .publicationYear((int) publicationYear)
                .author(Author.builder()
                        .fullName(fullName)
                        .build())
                .genre(Genre.builder()
                        .title(genre)
                        .build())
                .build());
        interactionService.outputString(MessageFormat.format("Book created with id = {0}", id));
    }

    @Override
    public void removeBook() {
        final var id = interactionService.readNumberByInterval("Book id: ", 1, Long.MAX_VALUE);
        bookService.delete(id);
    }

    @Override
    public void editBook() {
        final var id = interactionService.readNumberByInterval("Book id: ", 1, Long.MAX_VALUE);
        var book = bookService.read(id);
        if (book.isEmpty()) {
            interactionService.outputString("Book not found");
            return;
        }
        final var title = interactionService.readString("Title: ");
        final var publicationYear = interactionService.readNumberByInterval("Publication year: ", 1800, LocalDate.now().getYear());
        final var fullName = interactionService.readString("Author full name: ");
        final var genre = interactionService.readString("Genre: ");
        bookService.update(Book.builder()
                .id(id)
                .title(title)
                .publicationYear((int) publicationYear)
                .author(Author.builder()
                        .fullName(fullName)
                        .build())
                .genre(Genre.builder()
                        .title(genre)
                        .build())
                .build());
    }

    @Override
    public void printBooks() {
        var books = bookService.read();
        for (Book book : books) {
            final var bookInfo = MessageFormat.format("{0}) \"{1}\" {2}, {3}, {4}",
                    book.getId(), book.getTitle(), book.getAuthor().getFullName(), Integer.toString(book.getPublicationYear()), book.getGenre().getTitle());
            interactionService.outputString(bookInfo);
        }
    }

    @Override
    public void printAuthors() {
        var authors = authorService.read();
        for (Author author : authors) {
            final var bookInfo = MessageFormat.format("{0}) {1}", author.getId(), author.getFullName());
            interactionService.outputString(bookInfo);
        }
    }

    @Override
    public void printGenres() {
        var genres = genreService.read();
        for (Genre genre : genres) {
            final var bookInfo = MessageFormat.format("{0}) {1}", genre.getId(), genre.getTitle());
            interactionService.outputString(bookInfo);
        }
    }
}
