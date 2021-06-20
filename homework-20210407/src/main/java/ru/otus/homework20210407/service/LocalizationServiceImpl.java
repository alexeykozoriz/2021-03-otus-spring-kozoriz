package ru.otus.homework20210407.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Реализация по-умолчанию.
 * Строки получаются из i18n по ключу.
 */
@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;

    @Value("${application.locale}")
    private String locale;

    @Override
    public String getLocalizedString(String key, Object... objects) {
        return messageSource.getMessage(key, objects, Locale.forLanguageTag(locale));
    }
}
