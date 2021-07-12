package ru.otus.homework20210428.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework20210428.domain.Author;
import ru.otus.homework20210428.domain.Book;
import ru.otus.homework20210428.domain.BookComment;
import ru.otus.homework20210428.domain.Genre;

import java.text.MessageFormat;
import java.time.LocalDate;

/**
 * Реализация по-умолчанию.
 */
@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookCommentsService commentsService;
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
        final var book = Book.builder()
                .title(title)
                .publicationYear((int) publicationYear)
                .author(Author.builder()
                        .fullName(fullName)
                        .build())
                .genre(Genre.builder()
                        .title(genre)
                        .build())
                .build();
        bookService.save(book);
        interactionService.outputString(MessageFormat.format("Book created with id = {0}", book.getId()));
    }

    @Override
    public void removeBook(long id) {
        commentsService.deleteByBookId(id);
        bookService.delete(id);
    }

    @Override
    public void editBook(long bookId) {
        var bookOptional = bookService.read(bookId);
        if (bookOptional.isEmpty()) {
            interactionService.outputString("Book not found");
            return;
        }
        final var title = interactionService.readString("Title: ");
        final var publicationYear = interactionService.readNumberByInterval("Publication year: ", 1800, LocalDate.now().getYear());
        final var fullName = interactionService.readString("Author full name: ");
        final var genre = interactionService.readString("Genre: ");
        final var book = bookOptional.get();
        book.setTitle(title);
        book.setPublicationYear((int) publicationYear);
        book.setAuthor(Author.builder()
                .fullName(fullName)
                .build());
        book.setGenre(Genre.builder()
                .title(genre)
                .build());
        bookService.save(book);
    }

    @Override
    public void printBooks() {
        var books = bookService.read();
        for (Book book : books) {
            final var info = MessageFormat.format("{0}) \"{1}\" {2}, {3}, {4}",
                    book.getId(), book.getTitle(), book.getAuthor().getFullName(), Integer.toString(book.getPublicationYear()), book.getGenre().getTitle());
            interactionService.outputString(info);
        }
    }

    @Override
    public void printAuthors() {
        var authors = authorService.read();
        for (Author author : authors) {
            final var info = MessageFormat.format("{0}) {1}", author.getId(), author.getFullName());
            interactionService.outputString(info);
        }
    }

    @Override
    public void printGenres() {
        var genres = genreService.read();
        for (Genre genre : genres) {
            final var info = MessageFormat.format("{0}) {1}", genre.getId(), genre.getTitle());
            interactionService.outputString(info);
        }
    }

    @Override
    public void addComment(long bookId) {
        var bookOptional = bookService.read(bookId);
        if (bookOptional.isEmpty()) {
            interactionService.outputString("Book not found");
            return;
        }
        final var text = interactionService.readString("Text: ");
        final var author = interactionService.readString("Author: ");
        final var comment = BookComment.builder()
                .text(text)
                .author(author)
                .book(bookOptional.get())
                .build();
        commentsService.save(comment);
        interactionService.outputString(MessageFormat.format("Comment created with id = {0}", comment.getId()));
    }

    @Override
    public void printComments(long bookId) {
        final var commentsByBookId = commentsService.findByBookId(bookId);
        for (BookComment comment : commentsByBookId) {
            final var info = getCommentInfo(comment);
            interactionService.outputString(info);
        }
    }

    /**
     * Строка с идентификатором, текстом и автором комментария
     *
     * @param comment комментарий
     * @return строка
     */
    private String getCommentInfo(BookComment comment) {
        return MessageFormat.format("{0}: {1}", comment.getAuthor(), comment.getText());
    }
}
