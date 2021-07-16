package ru.otus.homework20210526.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework20210526.rest.dto.AuthorDto;
import ru.otus.homework20210526.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер авторов
 */
@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDto> getAll() {
        return authorService.read().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }
}
