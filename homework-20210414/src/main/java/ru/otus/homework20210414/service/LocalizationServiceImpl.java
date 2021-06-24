package ru.otus.homework20210414.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Реализация по-умолчанию.
 * Строки получаются из i18n по ключу.
 * Локаль получается из YML-конфига
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final String locale;

    public LocalizationServiceImpl(MessageSource messageSource,
                                   @Value("${application.locale}") String locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getLocalizedString(String key, Object... objects) {
        return messageSource.getMessage(key, objects, Locale.forLanguageTag(locale));
    }

    @Override
    public String getLocale() {
        return locale;
    }
}
