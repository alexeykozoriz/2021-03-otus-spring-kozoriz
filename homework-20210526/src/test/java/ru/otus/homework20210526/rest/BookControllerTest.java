package ru.otus.homework20210526.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210526.domain.Book;
import ru.otus.homework20210526.rest.dto.BookDto;
import ru.otus.homework20210526.service.BookCommentsService;
import ru.otus.homework20210526.service.BookService;

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
import static ru.otus.homework20210526.test.MockFactory.createBook;

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

    @DisplayName("Возврат всех")
    @Test
    void getAll() throws Exception {
        var books = List.of(createBook());
        given(bookService.read()).willReturn(books);
        var expected = books.stream().map(BookDto::toDto).collect(Collectors.toList());
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Возврат по идентификатору")
    @Test
    void getBook() throws Exception {
        var book = Optional.of(createBook());
        given(bookService.read(anyLong())).willReturn(book);
        var expected = BookDto.toDto(book.get());
        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Создание")
    @Test
    void postBook() throws Exception {
        var book = createBook();
        var dto = BookDto.toDto(book);

        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().bytes("1".getBytes(StandardCharsets.UTF_8)));

        then(bookService).should().save(book);
    }

    @DisplayName("Обновление")
    @Test
    void putBook() throws Exception {
        var book = createBook();
        var dto = BookDto.toDto(book);

        mvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        then(bookService).should().save(book);
    }

    @DisplayName("Удаление")
    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/books/1"))
                .andExpect(status().isOk());

        then(bookCommentsService).should().deleteByBookId(1L);
        then(bookService).should().delete(1L);
    }

    @DisplayName("Обработка ошибки")
    @Test
    void handleException() throws Exception {
        Optional<Book> bookEmpty = Optional.empty();
        given(bookService.read(anyLong())).willReturn(bookEmpty);
        mvc.perform(get("/books/2"))
                .andExpect(status().isNotFound());
    }
}