package ru.otus.homework20210607.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework20210607.rest.dto.AuthorDto;
import ru.otus.homework20210607.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер авторов
 */
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public List<AuthorDto> getAll() {
        return authorService.read().stream().map(AuthorDto::fromAuthor).collect(Collectors.toList());
    }
}
