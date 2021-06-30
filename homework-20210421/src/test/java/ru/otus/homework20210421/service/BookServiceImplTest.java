package ru.otus.homework20210421.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210421.dao.BookDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.otus.homework20210421.test.Constants.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис книг")
class BookServiceImplTest {

    @Mock
    private BookDao bookDao;
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Алгоритм create()")
    void create() {
        when(authorService.read(anyString())).thenReturn(null);
        when(authorService.create(any())).thenReturn(1L);
        when(genreService.read(anyString())).thenReturn(null);
        when(genreService.create(any())).thenReturn(2L);
        when(bookDao.insert(any())).thenReturn(ID_MOCK);
        var createdId = bookService.create(BOOK_MOCK);
        InOrder inOrder = inOrder(bookDao, authorService, genreService);
        inOrder.verify(authorService, times(1)).read(AUTHOR_MOCK.getFullName());
        inOrder.verify(authorService, times(1)).create(AUTHOR_MOCK);
        inOrder.verify(genreService, times(1)).read(GENRE_MOCK.getTitle());
        inOrder.verify(genreService, times(1)).create(GENRE_MOCK);
        inOrder.verify(bookDao, times(1)).insert(BOOK_MOCK_WITH_IDS);
        assertEquals(ID_MOCK, createdId);
    }

    @Test
    @DisplayName("Алгоритм update()")
    void update() {
        lenient().when(authorService.read(anyString())).thenReturn(null);
        lenient().when(authorService.create(any())).thenReturn(1L);
        lenient().when(genreService.read(anyString())).thenReturn(null);
        lenient().when(genreService.create(any())).thenReturn(2L);
        bookService.update(BOOK_MOCK);
        InOrder inOrder = inOrder(bookDao, authorService, genreService);
        inOrder.verify(bookDao, times(1)).update(BOOK_MOCK_WITH_IDS);
    }

    @Test
    @DisplayName("Алгоритм delete()")
    void delete() {
        bookService.delete(ID_MOCK);
        InOrder inOrder = inOrder(bookDao);
        inOrder.verify(bookDao, times(1)).deleteById(ID_MOCK);
    }

    @Test
    @DisplayName("Алгоритм read()")
    void read() {
        bookService.read();
        InOrder inOrder = inOrder(bookDao);
        inOrder.verify(bookDao, times(1)).getAll();
    }

    @Test
    @DisplayName("Алгоритм read(long id)")
    void readById() {
        when(bookDao.getById(anyLong())).thenReturn(BOOK_MOCK);
        bookService.read(ID_MOCK);
        InOrder inOrder = inOrder(bookDao);
        inOrder.verify(bookDao, times(1)).getById(ID_MOCK);
    }
}