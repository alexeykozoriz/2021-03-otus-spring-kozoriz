package ru.otus.homework20210607.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework20210607.rest.dto.GenreDto;
import ru.otus.homework20210607.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер жанров
 */
@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public List<GenreDto> getAll() {
        return genreService.read().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }
}
