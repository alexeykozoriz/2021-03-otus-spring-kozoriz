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
import ru.otus.homework20210609.rest.dto.BookCommentDto;
import ru.otus.homework20210609.service.BookCommentsService;
import ru.otus.homework20210609.service.BookService;

import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210609.test.MockFactory.*;
import static ru.otus.homework20210609.test.TokenUtility.getToken;

@DisplayName("Контроллер комментариев")
@SpringBootTest
@AutoConfigureMockMvc
class BookCommentControllerTest {

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

    @DisplayName("Возврат всех по идентификатору книги")
    @Test
    void getAll() throws Exception {
        var expected = bookCommentsService.findByBookId(1L).stream().map(BookCommentDto::fromComment).collect(Collectors.toList());
        mvc.perform(get("/api/comments/1").header(securityHeader, token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Создание")
    @Test
    void postComment() throws Exception {
        final var book = bookService.read().get(0);
        var expected = createBookComment(book);
        var dto = BookCommentDto.fromComment(expected);

        var responseString = mvc.perform(post("/api/comments").header(securityHeader, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        var id = Long.parseLong(responseString);
        var comments = bookCommentsService.findByBookId(book.getId());
        assertThat(comments).isNotNull().matches(p -> comments.stream().anyMatch(q -> q.getId() == id));
    }
}