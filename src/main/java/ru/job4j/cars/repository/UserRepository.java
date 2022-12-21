package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private static final String FROM_AUTO_USER_ORDER_BY_ID = "from auto_user order by id";
    private static final String FROM_AUTO_USER_WHERE_LOGIN_LIKE = "from auto_user where login like :key";
    private static final String FROM_AUTO_USER_WHERE_LOGIN = "from auto_user where login= :login";
    private static final String FROM_AUTO_USER_WHERE_ID = "from auto_user where id= :id";
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        crudRepository.run(session -> session.save(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.update(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
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
    public List<User> findAllOrderById() {
        return crudRepository.query(FROM_AUTO_USER_ORDER_BY_ID, User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        return crudRepository.optional(FROM_AUTO_USER_WHERE_ID, User.class, Map.of("id", id));
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(FROM_AUTO_USER_WHERE_LOGIN_LIKE, User.class, Map.of("key", "%" + key + "%"));
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(FROM_AUTO_USER_WHERE_LOGIN, User.class, Map.of("login", login));
    }
}
