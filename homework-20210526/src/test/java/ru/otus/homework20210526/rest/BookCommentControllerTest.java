package ru.otus.homework20210526.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210526.rest.dto.BookCommentDto;
import ru.otus.homework20210526.service.BookCommentsService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.homework20210526.test.MockFactory.createBookComment;

@DisplayName("Контроллер комментариев")
@WebMvcTest(BookCommentController.class)
class BookCommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookCommentsService bookCommentsService;

    @DisplayName("Возврат всех по идентификатору книги")
    @Test
    void getAll() throws Exception {
        var comments = List.of(createBookComment());
        given(bookCommentsService.findByBookId(anyLong())).willReturn(comments);
        var expected = comments.stream().map(BookCommentDto::toDto).collect(Collectors.toList());
        mvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @DisplayName("Создание")
    @Test
    void postComment() throws Exception {
        var comment = createBookComment();
        var dto = BookCommentDto.toDto(comment);

        mvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().bytes("1".getBytes(StandardCharsets.UTF_8)));

        then(bookCommentsService).should().save(comment);
    }
}