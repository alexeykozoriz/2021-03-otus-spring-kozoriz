package ru.otus.homework20210407.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Scanner;

/**
 * Реализация для взаимодействия через консоль
 */
@Service
public class InteractionServiceConsoleImpl implements InteractionService {

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        var scanner = new Scanner(System.in);
        String input;
        do {
            input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                System.out.println("Answer is required");
                continue;
            }
            break;
        } while (true);
        return input.trim();
    }

    @Override
    public Integer readIntByInterval(String prompt, int interval) {
        System.out.print(prompt);
        var scanner = new Scanner(System.in);
        Integer selected;
        do {
            String input = scanner.nextLine();
            if (!StringUtils.hasText(input)) {
                System.out.println("Answer is required");
                continue;
            }
            try {
                selected = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Option number must be an integer");
                continue;
            }
            if (selected < 1 || selected > interval) {
                System.out.printf("Option number must be in range of 1..%s%n", interval);
                continue;
            }
            break;
        } while (true);
        return selected;
    }

    @Override
    public void outputString(String text) {
        System.out.println(text);
    }
}
