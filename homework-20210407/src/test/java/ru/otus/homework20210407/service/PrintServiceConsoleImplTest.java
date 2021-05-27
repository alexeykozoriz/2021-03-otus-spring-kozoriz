package ru.otus.homework20210407.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.otus.homework20210407.domain.Question;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Класс PrintServiceConsoleImpl")
class PrintServiceConsoleImplTest {
    public static final String HAPPY = "happy";
    public static final String NULL = "null";
    public static final String EMPTY = "empty";
    public static final String NUMBER_ONLY = "number only";
    public static final String TEXT_ONLY = "text only";
    public static final String OPTIONS_ONLY = "options only";
    public static final String NULL_NUMBER = "null number";
    public static final String NULL_TEXT = "null text";
    public static final String NULL_OPTIONS = "null options";
    public static final String NON_DIGIT_NUMBER = "non-digit number";
    public static final String EMPTY_OPTIONS = "empty options";
    static Map<String, Question> questionCases = new HashMap<>();
    static Map<String, List<Question>> questionListCases = new HashMap<>();

    static {
        Question happy = new Question("1", "2+2 = ?", Arrays.asList("2", "22", "4"));
        questionCases.put(HAPPY, happy);
        questionCases.put(NULL, null);
        questionCases.put(EMPTY, new Question(null, null, null));
        questionCases.put(NUMBER_ONLY, new Question(happy.getNumber(), null, null));
        questionCases.put(TEXT_ONLY, new Question(null, happy.getText(), null));
        questionCases.put(OPTIONS_ONLY, new Question(null, null, happy.getOptions()));
        questionCases.put(NULL_NUMBER, new Question(null, happy.getText(), happy.getOptions()));
        questionCases.put(NULL_TEXT, new Question(happy.getNumber(), null, happy.getOptions()));
        questionCases.put(NULL_OPTIONS, new Question(happy.getNumber(), happy.getText(), null));
        questionCases.put(NON_DIGIT_NUMBER, new Question("A", happy.getText(), happy.getOptions()));
        questionCases.put(EMPTY_OPTIONS, new Question(happy.getNumber(), happy.getText(), new ArrayList<>()));
    }

    static {
        questionListCases.put(HAPPY, Arrays.asList(
                new Question("1", "2+2 = ?", Arrays.asList("2", "22", "4")),
                new Question("2", "2*2 = ?", Arrays.asList("2", "22", "4", "1"))));
        questionListCases.put(NULL, null);
        questionListCases.put(EMPTY, new ArrayList<>());
    }

    PrintServiceConsoleImpl printService;

    @BeforeEach
    void setUp() {
        printService = new PrintServiceConsoleImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @ValueSource(strings = {
            HAPPY,
            NULL,
            EMPTY,
            NUMBER_ONLY,
            TEXT_ONLY,
            OPTIONS_ONLY,
            NULL_NUMBER,
            NULL_TEXT,
            NULL_OPTIONS,
            NON_DIGIT_NUMBER,
            EMPTY_OPTIONS
    })
    void print(String caseName) {
        assertDoesNotThrow(() -> printService.print(questionCases.get(caseName)), caseName);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            HAPPY,
            NULL,
            EMPTY
    })
    void printAll(String caseName) {
        assertDoesNotThrow(() -> printService.printAll(questionListCases.get(caseName)), caseName);
    }
}