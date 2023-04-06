package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class PostServiceImplTest {

    @Test
    void whenCreateSuccess() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        PostService postService = new PostServiceImpl(postRepository, driverService, carService, engineService);
        Post post = new Post();
        Post postDb = new Post();
        postDb.setId(1);
        when(postRepository.create(post)).thenReturn(postDb);
        Post rsl = postService.create(post);
        verify(postRepository).create(post);
        assertThat(rsl).isEqualTo(post);
    }

    @Test
    void whenCreateFail() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        PostService postService = new PostServiceImpl(postRepository, driverService, carService, engineService);
        Post post = new Post();
        when(postRepository.create(post)).thenReturn(post);
        Throwable thrown = assertThrows(IllegalStateException.class, () -> postService.create(post));
        verify(postRepository).create(post);
        assertThat(thrown.getMessage()).isEqualTo("При сохранении данных произошла ошибка");
    }

    @Test
    void whenUpdateThenSuccess() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        HttpSession httpSession = mock(HttpSession.class);
        Post post = new Post();
        User postUser = new User();
        post.setUser(postUser);
        User sessionUser = new User();
        when(httpSession.getAttribute("user")).thenReturn(sessionUser);
        new PostServiceImpl(postRepository, driverService, carService, engineService).update(post, httpSession);
        verify(postRepository).update(post);
    }

    @Test
    void whenUpdateThenEx() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        HttpSession httpSession = mock(HttpSession.class);
        Post post = new Post();
        User postUser = new User();
        postUser.setId(1);
        post.setUser(postUser);
        User sessionUser = new User();
        when(httpSession.getAttribute("user")).thenReturn(sessionUser);
        Throwable thrown = assertThrows(IllegalArgumentException.class,
                () -> new PostServiceImpl(postRepository, driverService, carService, engineService).update(post, httpSession));
        assertThat(thrown.getMessage()).isEqualTo("Редактировать объявление может только пользователь, который его создал");
    }

    @Test
    void whenFindByIdThenExist() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        Post post = new Post();
        post.setId(1);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Post rslPost = new PostServiceImpl(postRepository, driverService, carService, engineService).findById(post.getId());
        verify(postRepository).findById(post.getId());
        assertThat(rslPost).isEqualTo(post);
    }

    @Test
    void whenFindByIdThenEx() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        Post post = new Post();
        post.setId(1);
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());
        Throwable thrown = assertThrows(NoSuchElementException.class,
                () -> new PostServiceImpl(postRepository, driverService, carService, engineService).findById(post.getId()));
        verify(postRepository).findById(post.getId());
        assertThat(thrown.getMessage()).isEqualTo("Объявление больше не доступно");
    }

    @Test
    void whenFindAllWithUserTzNotNullThenDateTimeConverted() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        PostService postService = new PostServiceImpl(postRepository, driverService, carService, engineService);
        String userTz = "Europe/Moscow";
        User user = new User();
        user.setTimezone(userTz);
        LocalDateTime ldt = LocalDateTime.of(2023, 1, 15, 1, 15);
        when(postRepository.findAll()).thenReturn(List.of(
                Post.builder()
                        .created(ldt)
                        .build())
        );
        List<Post> rsl = postService.findAll(user);
        verify(postRepository).findAll();
        LocalDateTime utc = ldt.minusSeconds(TimeZone.getTimeZone(ZoneId.systemDefault()).getRawOffset() / 1000);
        LocalDateTime exp = utc.plusSeconds(TimeZone.getTimeZone(userTz).getRawOffset() / 1000);
        assertThat(rsl.get(0).getCreated()).isEqualTo(exp);
    }

    @Test
    void whenFindAllWithUserTzNullThenDateTimeConvertedToDefaultTz() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        PostService postService = new PostServiceImpl(postRepository, driverService, carService, engineService);
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 14, 22, 15);
        when(postRepository.findAll()).thenReturn(List.of(
                Post.builder()
                        .created(localDateTime)
                        .build())
        );
        List<Post> rsl = postService.findAll(new User());
        verify(postRepository).findAll();
        assertThat(rsl.get(0).getCreated()).isEqualTo(localDateTime);
    }

    @Test
    void whenFindByStatusProxy() {
        PostRepository postRepository = mock(PostRepository.class);
        DriverService driverService = mock(DriverService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        new PostServiceImpl(postRepository, driverService, carService, engineService).findByStatus(true);
        verify(postRepository).findByStatus(true);
    }

    public static void main(String[] args) {
        System.out.println(TimeZone.getTimeZone(ZoneId.systemDefault()).getRawOffset());
    }
}