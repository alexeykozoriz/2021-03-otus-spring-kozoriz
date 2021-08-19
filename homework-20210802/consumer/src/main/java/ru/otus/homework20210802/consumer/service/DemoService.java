package ru.otus.homework20210802.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Демонстрационный сервис
 */
@Service
public class DemoService {

    private final String producerUrl;
    private final String producerUrlExternal;

    public DemoService(@Value("${producer.url:}") String producerUrl,
                       @Value("${producer.url-external:}") String producerUrlExternal) {
        this.producerUrl = producerUrl;
        this.producerUrlExternal = producerUrlExternal;
    }

    /**
     * Получение книг с контентом и внешними ссылками для формы
     *
     * @return список мап
     * @throws URISyntaxException исключение URI
     */
    @HystrixCommand(fallbackMethod = "getBooksDefault")
    public List<Map<String, Map<String, String>>> getBooks() throws URISyntaxException {
        final var traverson = new Traverson(new URI(producerUrl), MediaTypes.HAL_JSON);
        final var books = traverson
                .follow("books")
                .toObject(new TypeReferences.CollectionModelType<EntityModel<Map<String, Object>>>() {
                });
        final var fixed = new ArrayList<Map<String, Map<String, String>>>();
        assert books != null;
        books.getContent().forEach(p -> {
            final var item = new HashMap<String, Map<String, String>>();
            final var properties = Objects.requireNonNull(p.getContent()).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, q -> q.getValue().toString()));
            item.put("CONTENT", properties);
            final var links = p.getLinks().stream()
                    .filter(q -> !q.getRel().value().equalsIgnoreCase("book")
                            && !q.getRel().value().equalsIgnoreCase("self"))
                    .collect(Collectors.toMap(q -> q.getRel().value(), q -> q.getHref().replace(producerUrl, producerUrlExternal)));
            item.put("LINKS", links);
            fixed.add(item);
        });
        return fixed;
    }

    /**
     * Получение списка книг по-умолчанию
     *
     * @return список мап
     * @throws URISyntaxException исключение URI
     */
    public List<Map<String, Map<String, String>>> getBooksDefault() throws URISyntaxException {
        final var books = new ArrayList<Map<String, Map<String, String>>>();
        books.add(new HashMap<>());
        books.get(0).put("CONTENT", new HashMap<>());
        books.get(0).get("CONTENT").put("publicationYear", "1971");
        books.get(0).get("CONTENT").put("title", "The quick brown fox jumps over the lazy dog");
        books.get(0).put("LINKS", new HashMap<>());
        books.get(0).get("LINKS").put("author", "John Doe");
        books.get(0).get("LINKS").put("genre", "Default fiction");
        return books;
    }
}
