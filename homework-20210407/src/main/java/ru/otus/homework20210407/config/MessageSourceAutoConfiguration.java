package ru.otus.homework20210407.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Конфигурация источника сообщений
 */
@Configuration
public class MessageSourceAutoConfiguration {

    /**
     * Источник i18n.messages_ru
     *
     * @return источник сообщений
     */
    @Bean
    public MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n.messages");
        return messageSource;
    }

    /**
     * Пакет ресурсов i18n.messages_ru
     *
     * @return пакет ресурсов
     */
    @Bean
    public ResourceBundle resourceBundle() {
        return new MessageSourceResourceBundle(messageSource(), Locale.forLanguageTag("ru"));
    }
}
