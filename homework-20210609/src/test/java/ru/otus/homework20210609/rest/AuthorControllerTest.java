package ru.otus.homework20210609.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210609.domain.User;
import ru.otus.homework20210609.rest.dto.AuthorDto;
import ru.otus.homework20210609.service.AuthorService;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210609.test.MockFactory.JOHN_DOE;
import static ru.otus.homework20210609.test.MockFactory.JOHN_DOE_PASSWORD;
import static ru.otus.homework20210609.test.TokenUtility.getToken;

@DisplayName("Контроллер авторов")
@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthorService authorService;

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
        var expected = authorService.read().stream().map(AuthorDto::fromAuthor).collect(Collectors.toList());
        mvc.perform(get("/api/authors").header(securityHeader, token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }
}