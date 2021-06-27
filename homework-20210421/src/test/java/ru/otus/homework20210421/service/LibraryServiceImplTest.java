package ru.otus.homework20210421.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.otus.homework20210421.test.Constants.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис библиотеки")
class LibraryServiceImplTest {

    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;
    @Mock
    private BookService bookService;
    @Mock
    private InteractionService interactionService;
    @InjectMocks
    private LibraryServiceImpl libraryService;

    @Test
    @DisplayName("Алгоритм addBook()")
    void addBook() {
        when(interactionService.readString("Title: "))
                .thenReturn(BOOK_MOCK.getTitle());
        when(interactionService.readNumberByInterval("Publication year: ", 1800, LocalDate.now().getYear()))
                .thenReturn((long) BOOK_MOCK.getPublicationYear());
        when(interactionService.readString("Author full name: "))
                .thenReturn(BOOK_MOCK.getAuthor().getFullName());
        when(interactionService.readString("Genre: "))
                .thenReturn(BOOK_MOCK.getGenre().getTitle());
        when(bookService.create(any())).thenReturn(ID_MOCK);
        libraryService.addBook();
        InOrder inOrder = inOrder(interactionService, bookService);
        inOrder.verify(interactionService, times(1))
                .readString("Title: ");
        inOrder.verify(interactionService, times(1))
                .readNumberByInterval("Publication year: ", 1800, LocalDate.now().getYear());
        inOrder.verify(interactionService, times(1))
                .readString("Author full name: ");
        inOrder.verify(interactionService, times(1))
                .readString("Genre: ");
        inOrder.verify(bookService, times(1))
                .create(BOOK_MOCK);
        inOrder.verify(interactionService, times(1))
                .outputString(MessageFormat.format("Book created with id = {0}", ID_MOCK));
    }

    @Test
    @DisplayName("Алгоритм removeBook()")
    void removeBook() {
        when(interactionService.readNumberByInterval("Book id: ", 1, Long.MAX_VALUE))
                .thenReturn(ID_MOCK);
        libraryService.removeBook();
        InOrder inOrder = inOrder(interactionService, bookService);
        inOrder.verify(interactionService, times(1))
                .readNumberByInterval("Book id: ", 1, Long.MAX_VALUE);
        inOrder.verify(bookService, times(1))
                .delete(ID_MOCK);
    }

    @Test
    @DisplayName("Алгоритм editBook()")
    void editBook() {
        when(interactionService.readNumberByInterval("Book id: ", 1, Long.MAX_VALUE))
                .thenReturn(ID_MOCK);
        when(bookService.read(anyLong()))
                .thenReturn(Optional.of(BOOK_MOCK));
        when(interactionService.readString("Title: "))
                .thenReturn(BOOK_MOCK.getTitle());
        when(interactionService.readNumberByInterval("Publication year: ", 1800, LocalDate.now().getYear()))
                .thenReturn((long) BOOK_MOCK.getPublicationYear());
        when(interactionService.readString("Author full name: "))
                .thenReturn(BOOK_MOCK.getAuthor().getFullName());
        when(interactionService.readString("Genre: "))
                .thenReturn(BOOK_MOCK.getGenre().getTitle());
        libraryService.editBook();
        InOrder inOrder = inOrder(interactionService, bookService);
        inOrder.verify(interactionService, times(1))
                .readNumberByInterval("Book id: ", 1, Long.MAX_VALUE);
        inOrder.verify(bookService, times(1))
                .read(ID_MOCK);
        inOrder.verify(interactionService, times(1))
                .readString("Title: ");
        inOrder.verify(interactionService, times(1))
                .readNumberByInterval("Publication year: ", 1800, LocalDate.now().getYear());
        inOrder.verify(interactionService, times(1))
                .readString("Author full name: ");
        inOrder.verify(interactionService, times(1))
                .readString("Genre: ");
        inOrder.verify(bookService, times(1))
                .update(BOOK_MOCK_WITH_ID);
    }

    @Test
    @DisplayName("Алгоритм printBooks()")
    void printBooks() {
        when(bookService.read()).thenReturn(Collections.singletonList(BOOK_MOCK_WITH_ID));
        libraryService.printBooks();
        InOrder inOrder = inOrder(bookService, interactionService);
        inOrder.verify(bookService, times(1))
                .read();
        inOrder.verify(interactionService, times(1))
                .outputString(MessageFormat.format("{0}) \"{1}\" {2}, {3}, {4}",
                        BOOK_MOCK_WITH_ID.getId(), BOOK_MOCK_WITH_ID.getTitle(), BOOK_MOCK_WITH_ID.getAuthor().getFullName(), Integer.toString(BOOK_MOCK_WITH_ID.getPublicationYear()), BOOK_MOCK_WITH_ID.getGenre().getTitle()));
    }

    @Test
    @DisplayName("Алгоритм printAuthors()")
    void printAuthors() {
        when(authorService.read()).thenReturn(Collections.singletonList(AUTHOR_MOCK_WITH_ID));
        libraryService.printAuthors();
        InOrder inOrder = inOrder(authorService, interactionService);
        inOrder.verify(interactionService, times(1))
                .outputString(MessageFormat.format("{0}) {1}", AUTHOR_MOCK_WITH_ID.getId(), AUTHOR_MOCK_WITH_ID.getFullName()));
    }

    @Test
    @DisplayName("Алгоритм printGenres()")
    void printGenres() {
        when(genreService.read()).thenReturn(Collections.singletonList(GENRE_MOCK_WITH_ID));
        libraryService.printGenres();
        InOrder inOrder = inOrder(genreService, interactionService);
        inOrder.verify(interactionService, times(1))
                .outputString(MessageFormat.format("{0}) {1}", GENRE_MOCK_WITH_ID.getId(), GENRE_MOCK_WITH_ID.getTitle()));
    }
}