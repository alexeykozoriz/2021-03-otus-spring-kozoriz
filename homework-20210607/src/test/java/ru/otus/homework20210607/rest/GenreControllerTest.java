package ru.otus.homework20210607.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210607.rest.dto.GenreDto;
import ru.otus.homework20210607.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210607.test.MockFactory.createGenre;

@DisplayName("Контроллер жанров")
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserDetailsService userDetailsService;

    @DisplayName("Возврат всех (неавторизованный)")
    @Test
    void getAllUnauthorized() throws Exception {
        var genres = List.of(createGenre());
        given(genreService.read()).willReturn(genres);
        mvc.perform(get("/genres"))
                .andExpect(status().isFound());
    }

    @DisplayName("Возврат всех")
    @WithMockUser(username = "John Doe", authorities = {"USER"})
    @Test
    void getAll() throws Exception {
        var genres = List.of(createGenre());
        given(genreService.read()).willReturn(genres);
        var expected = genres.stream().map(GenreDto::toDto).collect(Collectors.toList());
        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }
}