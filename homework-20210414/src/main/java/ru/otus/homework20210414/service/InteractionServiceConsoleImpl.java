package ru.otus.homework20210414.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Scanner;

/**
 * Реализация для взаимодействия через консоль
 */
@Service
@RequiredArgsConstructor
public class InteractionServiceConsoleImpl implements InteractionService {

    private final InputStream inputStream;
    private final PrintStream printStream;
    private final LocalizationService localizationService;

    @Autowired
    public InteractionServiceConsoleImpl(LocalizationService localizationService) {
        this.inputStream = System.in;
        this.printStream = System.out;
        this.localizationService = localizationService;
    }

    @Override
    public String readString(String prompt) {
        printStream.print(prompt);
        var scanner = new Scanner(inputStream);
        String input;
        do {
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                printStream.println(localizationService.getLocalizedString("enter-a-string"));
                continue;
            }
            break;
        } while (true);
        return input.trim();
    }

    @Override
    public int readIntByInterval(String prompt, int interval) {
        printStream.print(prompt);
        var scanner = new Scanner(inputStream);
        int selected;
        do {
            String input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                printStream.println(localizationService.getLocalizedString("enter-a-string"));
                continue;
            }
            try {
                selected = Integer.parseInt(input);
            } catch (Exception e) {
                printStream.println(localizationService.getLocalizedString("enter-an-integer"));
                continue;
            }
            if (selected < 1 || selected > interval) {
                printStream.println(
                        MessageFormat.format(localizationService.getLocalizedString("number-must-be-in-range"), String.valueOf(interval)));
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
