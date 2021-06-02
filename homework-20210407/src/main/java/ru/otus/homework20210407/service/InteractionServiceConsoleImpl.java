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
    public String requestTextAnswer(String question) {
        System.out.print(question);
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
    public Integer requestOptionNumber(String question, int optionsSize) {
        System.out.print(question);
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
            if (selected < 1 || selected > optionsSize) {
                System.out.printf("Option number must be in range of 1..%s%n", optionsSize);
                continue;
            }
            break;
        } while (true);
        return selected;
    }

    @Override
    public void sendTextMessage(String text) {
        System.out.println(text);
    }
}
