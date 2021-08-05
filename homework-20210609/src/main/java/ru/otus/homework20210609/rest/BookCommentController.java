package ru.otus.homework20210609.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework20210609.rest.dto.BookCommentDto;
import ru.otus.homework20210609.service.BookCommentsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер комментариев
 */
@RestController
@RequiredArgsConstructor
public class BookCommentController {

    private final BookCommentsService bookCommentsService;

    @GetMapping("/api/comments/{id}")
    public List<BookCommentDto> getAll(@PathVariable("id") long id) {
        return bookCommentsService.findByBookId(id).stream().map(BookCommentDto::fromComment).collect(Collectors.toList());
    }

    @PostMapping("/api/comments")
    public Long postComment(@RequestBody BookCommentDto dto) {
        var bookComment = BookCommentDto.fromDto(dto);
        bookCommentsService.save(bookComment);
        return bookComment.getId();
    }
}
