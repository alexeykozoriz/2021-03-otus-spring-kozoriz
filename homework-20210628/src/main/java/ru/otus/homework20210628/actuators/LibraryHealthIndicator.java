package ru.otus.homework20210628.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.otus.homework20210628.domain.Book;
import ru.otus.homework20210628.repository.AuthorRepository;
import ru.otus.homework20210628.repository.BookCommentRepository;
import ru.otus.homework20210628.repository.BookRepository;
import ru.otus.homework20210628.repository.GenreRepository;

import java.text.MessageFormat;
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
    private final BookCommentRepository bookCommentRepository;
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
        final var mostPopularBook = allBooks.stream()
                .max(Comparator.comparing(p -> bookCommentRepository.findAllByBook_Id(p.getId(), Pageable.unpaged()).getSize()))
                .orElse(null);
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
                "Most commented book", mostPopularBook != null ? getBookInfo(mostPopularBook) : NO_FAVORITE_YET,
                "Authors count", allAuthors.size(),
                "Most popular author", mostPopularAuthor != null ? mostPopularAuthor.getFullName() : NO_FAVORITE_YET,
                "Genres count", allGenres.size(),
                "Most popular genre", mostPopularGenre != null ? mostPopularGenre.getTitle() : NO_FAVORITE_YET);
        return Health.up().withDetails(details).build();
    }

    /**
     * Информация о книге
     *
     * @param book книга
     * @return строка
     */
    private String getBookInfo(Book book) {
        return MessageFormat.format("{0}, {1}, {2}, {3}",
                book.getTitle(),
                book.getAuthor().getFullName(),
                book.getGenre().getTitle(),
                Integer.toString(book.getPublicationYear()));
    }
}
