package ru.otus.homework20210407.service;

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

    public static final String ANSWER_IS_REQUIRED = "Answer is required";
    public static final String OPTION_NUMBER_MUST_BE_AN_INTEGER = "Option number must be an integer";
    public static final String OPTION_NUMBER_MUST_BE_IN_RANGE = "Option number must be in range of 1..{0}";
    private final InputStream inputStream;
    private final PrintStream printStream;

    @Autowired
    public InteractionServiceConsoleImpl() {
        this.inputStream = System.in;
        this.printStream = System.out;
    }

    @Override
    public String readString(String prompt) {
        printStream.print(prompt);
        var scanner = new Scanner(inputStream);
        String input;
        do {
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                printStream.println(ANSWER_IS_REQUIRED);
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
                printStream.println(ANSWER_IS_REQUIRED);
                continue;
            }
            try {
                selected = Integer.parseInt(input);
            } catch (Exception e) {
                printStream.println(OPTION_NUMBER_MUST_BE_AN_INTEGER);
                continue;
            }
            if (selected < 1 || selected > interval) {
                printStream.println(
                        MessageFormat.format(OPTION_NUMBER_MUST_BE_IN_RANGE, String.valueOf(interval)));
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
