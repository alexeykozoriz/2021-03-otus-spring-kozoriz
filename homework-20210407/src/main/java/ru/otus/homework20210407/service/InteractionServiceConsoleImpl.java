package ru.otus.homework20210407.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Реализация для взаимодействия через консоль
 */
@Service
public class InteractionServiceConsoleImpl implements InteractionService {

    private final InputStream inputStream;
    private final PrintStream printStream;

    public InteractionServiceConsoleImpl() {
        this(System.in, System.out);
    }

    public InteractionServiceConsoleImpl(InputStream inputStream, PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    @Override
    public String readString(String prompt) {
        printStream.print(prompt);
        var scanner = new Scanner(inputStream);
        String input;
        do {
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                printStream.println("Answer is required");
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
                printStream.println("Answer is required");
                continue;
            }
            try {
                selected = Integer.parseInt(input);
            } catch (Exception e) {
                printStream.println("Option number must be an integer");
                continue;
            }
            if (selected < 1 || selected > interval) {
                printStream.printf("Option number must be in range of 1..%s%n", interval);
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
