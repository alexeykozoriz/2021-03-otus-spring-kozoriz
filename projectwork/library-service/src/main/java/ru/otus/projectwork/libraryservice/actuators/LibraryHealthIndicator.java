package ru.otus.projectwork.libraryservice.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.otus.projectwork.libraryservice.repository.AuthorRepository;
import ru.otus.projectwork.libraryservice.repository.BookRepository;
import ru.otus.projectwork.libraryservice.repository.GenreRepository;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Индикатор состояния библиотеки
 */
@RequiredArgsConstructor
@Component
public class LibraryHealthIndicator implements HealthIndicator {

    protected static final String NO_FAVORITE_YET = "No favorite yet";
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public Health health() {
        final var allBooks = StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allBooks)) {
            return Health.down()
                    .withDetail("message", "Books not found!")
                    .build();
        }
        final var allAuthors = StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        final var allGenres = StreamSupport
                .stream(genreRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        final var mostPopularAuthor = allAuthors.stream()
                .max(Comparator.comparing(p -> allBooks.stream()
                        .filter(q -> Objects.equals(q.getAuthor().getId(), p.getId()))
                        .count()))
                .orElse(null);
        final var mostPopularGenre = allGenres.stream()
                .max(Comparator.comparing(p -> allBooks.stream()
                        .filter(q -> Objects.equals(q.getGenre().getId(), p.getId()))
                        .count()))
                .orElse(null);
        final var details = Map.of(
                "Books count", allBooks.size(),
                "Authors count", allAuthors.size(),
                "Most popular author", mostPopularAuthor != null ? mostPopularAuthor.getFullName() : NO_FAVORITE_YET,
                "Genres count", allGenres.size(),
                "Most popular genre", mostPopularGenre != null ? mostPopularGenre.getTitle() : NO_FAVORITE_YET);
        return Health.up().withDetails(details).build();
    }
}
