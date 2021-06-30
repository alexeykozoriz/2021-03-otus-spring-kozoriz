package ru.otus.homework20210421.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework20210421.dao.AuthorDao;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static ru.otus.homework20210421.test.Constants.AUTHOR_FULLNAME_MOCK;
import static ru.otus.homework20210421.test.Constants.AUTHOR_MOCK;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис авторов")
class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    @DisplayName("Алгоритм create()")
    void create() {
        authorService.create(AUTHOR_MOCK);
        InOrder inOrder = inOrder(authorDao);
        inOrder.verify(authorDao, times(1)).insert(AUTHOR_MOCK);
    }

    @Test
    @DisplayName("Алгоритм read()")
    void read() {
        authorService.read();
        InOrder inOrder = inOrder(authorDao);
        inOrder.verify(authorDao, times(1)).getAll();
    }

    @Test
    @DisplayName("Алгоритм read(String fullnamePattern)")
    void readById() {
        authorService.read(AUTHOR_FULLNAME_MOCK);
        InOrder inOrder = inOrder(authorDao);
        inOrder.verify(authorDao, times(1)).getByFullname(AUTHOR_FULLNAME_MOCK);
    }
}