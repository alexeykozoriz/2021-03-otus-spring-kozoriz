package ru.otus.homework20210609.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework20210609.rest.dto.GenreDto;
import ru.otus.homework20210609.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер жанров
 */
@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/api/genres")
    public List<GenreDto> getAll() {
        return genreService.read().stream().map(GenreDto::fromGenre).collect(Collectors.toList());
    }
}
