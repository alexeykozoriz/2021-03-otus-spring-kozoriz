package ru.otus.homework20210526.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210526.rest.dto.AuthorDto;
import ru.otus.homework20210526.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210526.test.MockFactory.createAuthor;

@DisplayName("Контроллер авторов")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @DisplayName("Возврат всех")
    @Test
    void getAll() throws Exception {
        var authors = List.of(createAuthor());
        given(authorService.read()).willReturn(authors);
        var expected = authors.stream().map(AuthorDto::toDto).collect(Collectors.toList());
        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }
}