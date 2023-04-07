package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String FROM_AUTO_USER_ORDER_BY_ID = "from User order by id";
    private static final String FROM_AUTO_USER_WHERE_LOGIN_LIKE = "from User where login like :key";
    private static final String FROM_AUTO_USER_WHERE_LOGIN = "from User where login= :login";
    private static final String FROM_AUTO_USER_WHERE_ID = "from User where id= :id";
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> create(User user) {
        try {
            crudRepository.run(session -> session.save(user));
            return Optional.of(user);
        } catch (IllegalStateException ex) {
            return Optional.empty();
        }
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.update(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public void delete(int userId) {
        User user = new User();
        user.setId(userId);
        crudRepository.run(session -> session.delete(user));
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        return crudRepository.query(FROM_AUTO_USER_ORDER_BY_ID, User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional(FROM_AUTO_USER_WHERE_ID, User.class, Map.of("id", id));
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    @Override
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(FROM_AUTO_USER_WHERE_LOGIN_LIKE, User.class, Map.of("key", "%" + key + "%"));
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(FROM_AUTO_USER_WHERE_LOGIN, User.class, Map.of("login", login));
    }
}
