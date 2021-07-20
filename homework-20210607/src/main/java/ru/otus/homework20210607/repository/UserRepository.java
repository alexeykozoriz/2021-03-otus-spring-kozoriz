package ru.otus.homework20210607.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework20210607.domain.User;

import java.util.Optional;

/**
 * Хранилище пользователей
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserName(String userName);

}
