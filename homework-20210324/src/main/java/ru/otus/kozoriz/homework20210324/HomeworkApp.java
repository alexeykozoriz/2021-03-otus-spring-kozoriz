package ru.otus.kozoriz.homework20210324;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.kozoriz.homework20210324.service.QuestionsPrintService;
import ru.otus.kozoriz.homework20210324.service.QuestionsService;

public class HomeworkApp {

    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var questionsService = context.getBean(QuestionsService.class);
        var questionsPrintService = context.getBean(QuestionsPrintService.class);
        questionsPrintService.printAll(
                questionsService.findAllQuestions());
    }
}
