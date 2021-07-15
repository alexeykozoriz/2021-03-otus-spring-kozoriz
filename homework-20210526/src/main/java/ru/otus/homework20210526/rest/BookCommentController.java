package ru.otus.homework20210526.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework20210526.rest.dto.BookCommentDto;
import ru.otus.homework20210526.service.BookCommentsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер комментариев
 */
@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class BookCommentController {

    private final BookCommentsService bookCommentsService;

    @GetMapping("/{id}")
    public List<BookCommentDto> getAll(@PathVariable("id") long id) {
        return bookCommentsService.findByBookId(id).stream().map(BookCommentDto::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public Long postComment(@RequestBody BookCommentDto dto) {
        var bookComment = BookCommentDto.toDomainObject(dto);
        bookCommentsService.save(bookComment);
        return bookComment.getId();
    }
}
