package ru.otus.homework20210526.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework20210526.rest.dto.GenreDto;
import ru.otus.homework20210526.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер жанров
 */
@RestController
@RequestMapping("genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> getAll() {
        return genreService.read().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }
}
