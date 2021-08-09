package ru.otus.homework20210705.consumer.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Демонстрационный контроллер
 */
@Controller
public class DemoController {

    @GetMapping
    public String get(Model model) throws URISyntaxException {
        var traverson = new Traverson(new URI("http://localhost:8081"), MediaTypes.HAL_JSON);
        var books = traverson
                .follow("books")
                .toObject(new TypeReferences.CollectionModelType<EntityModel<Map<String, Object>>>(){});
        model.addAttribute("books", books);
        return "demo";
    }
}
