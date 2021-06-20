package ru.otus.homework20210407.service;

/**
 * Сервисный класс для поучения локализованных ресурсов
 */
public interface LocalizationService {
    /**
     * Локализованная строка
     *
     * @param key     ключ ресурса
     * @param objects объеты для форматирования
     * @return строка
     */
    String getLocalizedString(String key, Object... objects);

    /**
     * Локализованная строка
     *
     * @param key ключ ресурса
     * @return строка
     */
    default String getLocalizedString(String key) {
        return getLocalizedString(key, (Object[]) null);
    }
}
