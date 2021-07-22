package ru.otus.homework20210607.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210607.domain.Book;
import ru.otus.homework20210607.rest.dto.BookDto;
import ru.otus.homework20210607.service.BookCommentsService;
import ru.otus.homework20210607.service.BookService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210607.test.MockFactory.createBook;

@DisplayName("Контроллер книг")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookCommentsService bookCommentsService;

    @MockBean
    private UserDetailsService userDetailsService;

    @DisplayName("Возврат всех (неавторизованный)")
    @Test
    void getAllUnauthorized() throws Exception {
        var books = List.of(createBook());
        given(bookService.read()).willReturn(books);
        mvc.perform(get("/books"))
                .andExpect(status().isFound());
    }

    @DisplayName("Возврат всех")
    @WithMockUser(username = "John Doe", authorities = {"USER"})
    @Test
    void getAll() throws Exception {
        var books = List.of(createBook());
        given(bookService.read()).willReturn(books);
        var expected = books.stream().map(BookDto::fromBook).collect(Collectors.toList());
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Возврат по идентификатору (неавторизованный)")
    @Test
    void getBookUnauthorized() throws Exception {
        var book = Optional.of(createBook());
        given(bookService.read(anyLong())).willReturn(book);
        var expected = BookDto.fromBook(book.get());
        mvc.perform(get("/books/1"))
                .andExpect(status().isFound());
    }

    @DisplayName("Возврат по идентификатору")
    @WithMockUser(username = "John Doe", authorities = {"USER"})
    @Test
    void getBook() throws Exception {
        var book = Optional.of(createBook());
        given(bookService.read(anyLong())).willReturn(book);
        var expected = BookDto.fromBook(book.get());
        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Создание (неавторизованное)")
    @Test
    void postBookUnauthorized() throws Exception {
        var book = createBook();
        var dto = BookDto.fromBook(book);

        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isFound());

        then(bookService).shouldHaveNoInteractions();
    }

    @DisplayName("Создание")
    @WithMockUser(username = "John Doe", authorities = {"USER"})
    @Test
    void postBook() throws Exception {
        var book = createBook();
        var dto = BookDto.fromBook(book);

        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().bytes("1".getBytes(StandardCharsets.UTF_8)));

        then(bookService).should().save(book);
    }

    @DisplayName("Обновление (неавторизованное)")
    @Test
    void putBookUnauthorized() throws Exception {
        var book = createBook();
        var dto = BookDto.fromBook(book);

        mvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isFound());

        then(bookService).shouldHaveNoInteractions();
    }

    @DisplayName("Обновление")
    @WithMockUser(username = "John Doe", authorities = {"USER"})
    @Test
    void putBook() throws Exception {
        var book = createBook();
        var dto = BookDto.fromBook(book);

        mvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        then(bookService).should().save(book);
    }

    @DisplayName("Удаление (неавторизованное)")
    @Test
    void deleteBookUnauthorized() throws Exception {
        mvc.perform(delete("/books/1"))
                .andExpect(status().isFound());

        then(bookCommentsService).shouldHaveNoInteractions();
        then(bookService).shouldHaveNoInteractions();
    }

    @DisplayName("Удаление")
    @WithMockUser(username = "John Doe", authorities = {"USER"})
    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/books/1"))
                .andExpect(status().isOk());

        then(bookCommentsService).should().deleteByBookId(1L);
        then(bookService).should().delete(1L);
    }

    @DisplayName("Обработка ошибки")
    @WithMockUser(username = "John Doe", authorities = {"USER"})
    @Test
    void handleException() throws Exception {
        Optional<Book> bookEmpty = Optional.empty();
        given(bookService.read(anyLong())).willReturn(bookEmpty);
        mvc.perform(get("/books/2"))
                .andExpect(status().isNotFound());
    }
}