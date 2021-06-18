package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

/**
 * Реализация для взаимодействия через консоль
 */
@Service
@RequiredArgsConstructor
public class InteractionServiceConsoleImpl implements InteractionService {

    public static final Locale LOCALE = Locale.forLanguageTag("ru-RU");
    private final InputStream inputStream;
    private final PrintStream printStream;
    private final MessageSource messageSource;

    @Autowired
    public InteractionServiceConsoleImpl(MessageSource messageSource) {
        this.inputStream = System.in;
        this.printStream = System.out;
        this.messageSource = messageSource;
    }

    @Override
    public String readString(String prompt) {
        printStream.print(prompt);
        var scanner = new Scanner(inputStream);
        String input;
        do {
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                printStream.println(
                        messageSource.getMessage("answer-required", null, LOCALE));
                continue;
            }
            break;
        } while (true);
        return input.trim();
    }

    @Override
    public Integer readIntByInterval(String prompt, int interval) {
        printStream.print(prompt);
        var scanner = new Scanner(inputStream);
        int selected;
        do {
            String input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                printStream.println(
                        messageSource.getMessage("answer-required", null, LOCALE));
                continue;
            }
            try {
                selected = Integer.parseInt(input);
            } catch (Exception e) {
                printStream.println(
                        messageSource.getMessage("option-must-be-an-integer", null, LOCALE));
                continue;
            }
            if (selected < 1 || selected > interval) {
                printStream.println(
                        messageSource.getMessage("option-must-be-in-range", new String[]{String.valueOf(interval)}, LOCALE));
                continue;
            }
            break;
        } while (true);
        return selected;
    }

    @Override
    public void outputString(String text) {
        printStream.println(text);
    }
}
