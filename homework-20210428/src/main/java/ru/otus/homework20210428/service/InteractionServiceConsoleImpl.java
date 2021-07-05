package ru.otus.homework20210428.service;

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

    public static final String STRING_REQUIRED = "String required";
    private final InputStream in;
    private final PrintStream out;

    public InteractionServiceConsoleImpl() {
        this(System.in, System.out);
    }

    public InteractionServiceConsoleImpl(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public String readString(String prompt) {
        out.print(prompt);
        var scanner = new Scanner(in);
        String input;
        do {
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                out.println(STRING_REQUIRED);
                continue;
            }
            break;
        } while (true);
        return input.trim();
    }

    @Override
    public long readNumberByInterval(String prompt, long minimum, long maximum) {
        out.print(prompt);
        var scanner = new Scanner(in);
        long selected;
        do {
            String input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                out.println(STRING_REQUIRED);
                continue;
            }
            try {
                selected = Long.parseLong(input);
            } catch (Exception e) {
                out.println("Integer required");
                continue;
            }
            if (selected < minimum || selected > maximum) {
                out.printf("Value must be in range of %s..%s%n", minimum, maximum);
                continue;
            }
            break;
        } while (true);
        return selected;
    }

    @Override
    public void outputString(String text) {
        out.println(text);
    }
}
