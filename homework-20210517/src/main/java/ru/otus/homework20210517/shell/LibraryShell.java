package ru.otus.homework20210517.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework20210517.service.LibraryService;

/**
 * Интерфейсный компонент для взаимодействия с библиотекой
 */
@ShellComponent
@RequiredArgsConstructor
public class LibraryShell {

    private final LibraryService libraryService;

    /**
     * Создание книги
     */
    @ShellMethod(key = {"add-book", "ab"}, value = "Create book")
    private void createBook() {
        libraryService.addBook();
    }

    /**
     * Удаление книги
     */
    @ShellMethod(key = {"remove-book", "rb"}, value = "Remove book")
    private void removeBook(long bookId) {
        libraryService.removeBook(bookId);
    }

    /**
     * Редактирование книги
     */
    @ShellMethod(key = {"edit-book", "eb"}, value = "Edit book")
    private void editBook(long bookId) {
        libraryService.editBook(bookId);
    }

    /**
     * Вывод книг
     */
    @ShellMethod(key = {"print-books", "pb"}, value = "Print books")
    private void printBooks() {
        libraryService.printBooks();
    }

    /**
     * Вывод авторов
     */
    @ShellMethod(key = {"print-authors", "pa"}, value = "Print authors")
    private void printAuthors() {
        libraryService.printAuthors();
    }

    /**
     * Вывод жанров
     */
    @ShellMethod(key = {"print-genres", "pg"}, value = "Print genres")
    private void printGenres() {
        libraryService.printGenres();
    }

    /**
     * Добавление комментария
     */
    @ShellMethod(key = {"add-comment", "ac"}, value = "Add comment")
    private void addComment(long bookId) {
        libraryService.addComment(bookId);
    }

    /**
     * Вывод комментариев
     */
    @ShellMethod(key = {"print-comments", "pc"}, value = "Print comments")
    private void printComments(long bookId) {
        libraryService.printComments(bookId);
    }
}
