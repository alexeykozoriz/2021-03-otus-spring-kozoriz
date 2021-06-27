package ru.otus.homework20210421.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210421.dao.GenreDao;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static ru.otus.homework20210421.test.Constants.GENRE_MOCK;
import static ru.otus.homework20210421.test.Constants.GENRE_TITLE_MOCK;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис жанров")
class GenreServiceImplTest {

    @Mock
    private GenreDao genreDao;
    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    @DisplayName("Алгоритм create()")
    void create() {
        genreService.create(GENRE_MOCK);
        InOrder inOrder = inOrder(genreDao);
        inOrder.verify(genreDao, times(1)).insert(GENRE_MOCK);
    }

    @Test
    @DisplayName("Алгоритм read()")
    void read() {
        genreService.read();
        InOrder inOrder = inOrder(genreDao);
        inOrder.verify(genreDao, times(1)).getAll();
    }

    @Test
    @DisplayName("Алгоритм read(String titlePattern)")
    void readById() {
        genreService.read(GENRE_TITLE_MOCK);
        InOrder inOrder = inOrder(genreDao);
        inOrder.verify(genreDao, times(1)).getByTitle(GENRE_TITLE_MOCK);
    }
}