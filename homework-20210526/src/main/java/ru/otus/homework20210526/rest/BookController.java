package ru.otus.homework20210526.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework20210526.rest.dto.BookDto;
import ru.otus.homework20210526.rest.exception.NotFoundException;
import ru.otus.homework20210526.service.BookCommentsService;
import ru.otus.homework20210526.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер книг
 */
@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookCommentsService bookCommentsService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.read().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        var book = bookService.read(id).orElseThrow(NotFoundException::new);
        return BookDto.toDto(book);
    }

    @PostMapping
    public Long postBook(@RequestBody BookDto dto) {
        var book = BookDto.toDomainObject(dto);
        bookService.save(book);
        return book.getId();
    }

    @PutMapping
    public void putBook(@RequestBody BookDto dto) {
        var book = BookDto.toDomainObject(dto);
        bookService.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookCommentsService.deleteByBookId(id);
        bookService.delete(id);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
