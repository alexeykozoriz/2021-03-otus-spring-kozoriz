package ru.otus.homework20210609.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework20210609.rest.dto.BookDto;
import ru.otus.homework20210609.rest.exception.NotFoundException;
import ru.otus.homework20210609.service.BookCommentsService;
import ru.otus.homework20210609.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер книг
 */
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookCommentsService bookCommentsService;

    @GetMapping("/api/books")
    public List<BookDto> getAll() {
        return bookService.read().stream().map(BookDto::fromBook).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        var book = bookService.read(id).orElseThrow(NotFoundException::new);
        return BookDto.fromBook(book);
    }

    @PostMapping("/api/books")
    public Long postBook(@RequestBody BookDto dto) {
        var book = BookDto.fromDto(dto);
        bookService.save(book);
        return book.getId();
    }

    @PutMapping("/api/books")
    public void putBook(@RequestBody BookDto dto) {
        var book = BookDto.fromDto(dto);
        bookService.save(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookCommentsService.deleteByBookId(id);
        bookService.delete(id);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
