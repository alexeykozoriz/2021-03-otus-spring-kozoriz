package ru.otus.homework20210609.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210609.domain.User;
import ru.otus.homework20210609.rest.dto.BookDto;
import ru.otus.homework20210609.service.BookCommentsService;
import ru.otus.homework20210609.service.BookService;

import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210609.test.MockFactory.*;
import static ru.otus.homework20210609.test.TokenUtility.getToken;

@DisplayName("Контроллер книг")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookCommentsService bookCommentsService;

    @Value("${security.header}")
    private String securityHeader;

    @Value("${security.sign-up-url}")
    private String securitySignUpUrl;

    @Value("${security.token-prefix}")
    private String securityTokenPrefix;

    private String token;

    @BeforeEach
    void setUp() {
        token = getToken(User.builder().userName(JOHN_DOE).password(JOHN_DOE_PASSWORD).build(), mvc, mapper, securitySignUpUrl, securityTokenPrefix);
    }

    @DisplayName("Возврат всех")
    @Test
    void getAll() throws Exception {
        var expected = bookService.read().stream().map(BookDto::fromBook).collect(Collectors.toList());
        mvc.perform(get("/api/books").header(securityHeader, token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Возврат по идентификатору")
    @Test
    void getBook() throws Exception {
        var expected = BookDto.fromBook(bookService.read(1L).orElseThrow());
        mvc.perform(get("/api/books/1").header(securityHeader, token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Создание")
    @Test
    void postBook() throws Exception {
        var book = createBook();
        var dto = BookDto.fromBook(book);

        var responseString = mvc.perform(post("/api/books").header(securityHeader, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        var id = Long.parseLong(responseString);
        assertThat(bookService.read(id)).isPresent();
    }

    @DisplayName("Обновление")
    @Test
    void putBook() throws Exception {
        var book = bookService.read().get(0);
        book.setGenre(createGenre());
        var dto = BookDto.fromBook(book);

        mvc.perform(put("/api/books").header(securityHeader, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        assertThat(bookService.read(book.getId()).orElseThrow())
                .matches(p -> p.getGenre().getTitle().equals(book.getGenre().getTitle()));
    }

    @DisplayName("Удаление")
    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/api/books/1").header(securityHeader, token))
                .andExpect(status().isOk());

        assertThat(bookService.read(1L)).isEmpty();
    }

    @DisplayName("Обработка ошибки")
    @Test
    void handleException() throws Exception {
        mvc.perform(get("/api/books/-1").header(securityHeader, token))
                .andExpect(status().isNotFound());
    }
}