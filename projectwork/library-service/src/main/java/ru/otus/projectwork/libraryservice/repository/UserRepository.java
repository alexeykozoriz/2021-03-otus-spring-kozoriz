package ru.otus.projectwork.libraryservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.projectwork.libraryservice.domain.User;

import java.util.Optional;

/**
 * Хранилище пользователей
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserName(String userName);

}
