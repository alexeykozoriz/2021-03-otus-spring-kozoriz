package ru.otus.homework20210707.consumer.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Демонстрационный контроллер
 */
@Controller
public class DemoController {

    private final String producerUrl;
    private final String producerUrlExternal;

    public DemoController(@Value("${producer.url:}") String producerUrl,
                          @Value("${producer.url-external:}") String producerUrlExternal) {
        this.producerUrl = producerUrl;
        this.producerUrlExternal = producerUrlExternal;
    }

    @GetMapping
    public String get(Model model) throws URISyntaxException {
        final var traverson = new Traverson(new URI(producerUrl), MediaTypes.HAL_JSON);
        final var books = traverson
                .follow("books")
                .toObject(new TypeReferences.CollectionModelType<EntityModel<Map<String, Object>>>() {
                });
        assert books != null;
        model.addAttribute("books", fixUrls(books));
        return "demo";
    }

    /**
     * Замена кластерных ссылок на внешние
     *
     * @param books книги
     * @return список мап
     */
    private ArrayList<Map<String, Map<String, String>>> fixUrls(CollectionModel<EntityModel<Map<String, Object>>> books) {
        final var fixed = new ArrayList<Map<String, Map<String, String>>>();
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
}
