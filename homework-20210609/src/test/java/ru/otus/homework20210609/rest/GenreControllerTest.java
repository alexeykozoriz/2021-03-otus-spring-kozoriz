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
import ru.otus.homework20210609.rest.dto.GenreDto;
import ru.otus.homework20210609.service.GenreService;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210609.test.MockFactory.JOHN_DOE;
import static ru.otus.homework20210609.test.MockFactory.JOHN_DOE_PASSWORD;
import static ru.otus.homework20210609.test.TokenUtility.getToken;

@DisplayName("Контроллер жанров")
@SpringBootTest
@AutoConfigureMockMvc
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private GenreService genreService;

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
        var expected = genreService.read().stream().map(GenreDto::fromGenre).collect(Collectors.toList());
        mvc.perform(get("/api/genres").header(securityHeader, token))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }
}