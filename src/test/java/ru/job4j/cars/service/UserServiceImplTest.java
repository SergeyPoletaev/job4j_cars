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
        UserService userService = new UserServiceImpl(userRepository);
        User user = new User();
        userService.create(user);
        verify(userRepository).create(user);
    }

    @Test
    void whenUpdateThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);
        User user = new User();
        userService.update(user);
        verify(userRepository).update(user);
    }

    @Test
    void whenDeleteThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);
        int userId = 2;
        userService.delete(userId);
        verify(userRepository).delete(userId);
    }

    @Test
    void whenFindAllOrderByIdThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);
        userService.findAllOrderById();
        verify(userRepository).findAllOrderById();
    }

    @Test
    void whenFindByIdThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);
        int id = 1;
        userService.findById(id);
        verify(userRepository).findById(id);
    }

    @Test
    void whenFindByLikeLoginThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);
        String key = "key";
        userService.findByLikeLogin(key);
        verify(userRepository).findByLikeLogin(key);
    }

    @Test
    void whenFindByLoginThenProxy() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);
        String login = "login";
        userService.findByLogin(login);
        verify(userRepository).findByLogin(login);
    }
}