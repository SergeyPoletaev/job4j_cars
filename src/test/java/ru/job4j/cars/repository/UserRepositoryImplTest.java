package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.User;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryImplTest {
    private static SessionFactory sf;

    @BeforeAll
    static void init() {
        sf = new HbmTestConfig().getSessionFactory();
    }

    @AfterAll
    static void close() {
        sf.close();
    }

    @AfterEach
    void cleanDb() {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from auto_user").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    @Test
    void whenCreateSomeUserThenFindThisUser() {
        UserRepository userRepository = new UserRepositoryImpl(new CrudRepositoryImpl(sf));
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("123");
        userRepository.create(user);
        assertThat(userRepository.findById(user.getId()).orElseThrow().getLogin())
                .isEqualTo("testUser");
    }

    @Test
    void whenUpdateUserThenFindUpdatedUser() {
        UserRepository userRepository = new UserRepositoryImpl(new CrudRepositoryImpl(sf));
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("123");
        userRepository.create(user);
        user.setLogin("newUser");
        userRepository.update(user);
        assertThat(userRepository.findById(user.getId()).orElseThrow().getLogin())
                .isEqualTo("newUser");
    }

    @Test
    void whenDeleteUserThenThisUserNotFound() {
        UserRepository userRepository = new UserRepositoryImpl(new CrudRepositoryImpl(sf));
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("123");
        userRepository.create(user);
        userRepository.delete(user.getId());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenFindOrdered() {
        UserRepository userRepository = new UserRepositoryImpl(new CrudRepositoryImpl(sf));
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("123");
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("456");
        User user3 = new User();
        user3.setLogin("user3");
        user3.setPassword("789");
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);
        List<Integer> ids = userRepository.findAllOrderById().stream()
                .map(User::getId)
                .toList();
        assertThat(ids.get(0)).isEqualTo(user1.getId());
        assertThat(ids.get(1)).isEqualTo(user2.getId());
        assertThat(ids.get(2)).isEqualTo(user3.getId());
    }

    @Test
    void whenFindByLikeLoginThenFindToThePattern() {
        UserRepository userRepository = new UserRepositoryImpl(new CrudRepositoryImpl(sf));
        User user1 = new User();
        user1.setLogin("testUser");
        user1.setPassword("123");
        User user2 = new User();
        user2.setLogin("MyUser");
        user2.setPassword("456");
        userRepository.create(user1);
        userRepository.create(user2);
        assertThat(userRepository.findByLikeLogin("yU").get(0).getLogin()).isEqualTo("MyUser");
        assertThat(userRepository.findByLikeLogin("ser").size()).isEqualTo(2);
        assertThat(userRepository.findByLikeLogin("Ser")).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void whenFindByLoginThenFindUserWithThisLogin() {
        UserRepository userRepository = new UserRepositoryImpl(new CrudRepositoryImpl(sf));
        User user1 = new User();
        user1.setLogin("testUser");
        user1.setPassword("123");
        User user2 = new User();
        user2.setLogin("testUser2");
        user2.setPassword("456");
        userRepository.create(user1);
        userRepository.create(user2);
        assertThat(userRepository.findByLogin("testUser").orElseThrow()).isEqualTo(user1);
    }
}