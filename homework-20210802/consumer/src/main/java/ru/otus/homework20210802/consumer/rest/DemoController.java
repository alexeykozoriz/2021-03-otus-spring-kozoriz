package ru.otus.homework20210802.consumer.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework20210802.consumer.service.DemoService;

import java.net.URISyntaxException;

/**
 * Демонстрационный контроллер
 */
@Controller
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @GetMapping
    public String get(Model model) throws URISyntaxException {
        model.addAttribute("books", demoService.getBooks());
        return "demo";
    }
}
