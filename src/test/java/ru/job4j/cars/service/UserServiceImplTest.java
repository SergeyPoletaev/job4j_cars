package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    @Test
    void whenCreateThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        User user = new User();
        new UserServiceImpl(userRepository).create(user);
        verify(userRepository).create(user);
    }

    @Test
    void whenUpdateThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        User user = new User();
        new UserServiceImpl(userRepository).update(user);
        verify(userRepository).update(user);
    }

    @Test
    void whenDeleteThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        int userId = 2;
        new UserServiceImpl(userRepository).delete(userId);
        verify(userRepository).delete(userId);
    }

    @Test
    void whenFindAllOrderByIdThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        new UserServiceImpl(userRepository).findAllOrderById();
        verify(userRepository).findAllOrderById();
    }

    @Test
    void whenFindByIdThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        int id = 1;
        new UserServiceImpl(userRepository).findById(id);
        verify(userRepository).findById(id);
    }

    @Test
    void whenFindByLikeLoginThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        String key = "key";
        new UserServiceImpl(userRepository).findByLikeLogin(key);
        verify(userRepository).findByLikeLogin(key);
    }

    @Test
    void whenFindByLoginThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        String login = "login";
        new UserServiceImpl(userRepository).findByLogin(login);
        verify(userRepository).findByLogin(login);
    }
}