package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.PostRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private DriverService driverService;
    private CarService carService;
    private EngineService engineService;


    @Override
    public Post create(Post post) {
        if (postRepository.create(post).getId() == 0) {
            throw new IllegalStateException("При сохранении данных произошла ошибка");
        }
        return post;
    }

    @Override
    public Post createPostFromRequestParam(HttpServletRequest req, HttpSession httpSession) throws Exception {
        Map<String, ?> map = getParams(req, httpSession);
        driverService.create((Driver) map.get("driver"));
        engineService.create((Engine) map.get("engine"));
        carService.create((Car) map.get("car"));
        return create((Post) map.get("post"));
    }

    @Override
    public void update(Post post, HttpSession httpSession) {
        if (!post.getUser().equals(httpSession.getAttribute("user"))) {
            throw new IllegalArgumentException("Редактировать объявление может только пользователь, который его создал");
        }
        postRepository.update(post);
    }

    @Override
    public Post findById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Объявление больше не доступно"));
    }

    @Override
    public List<Post> findAll(User user) {
        List<Post> posts = postRepository.findAll();
        setUserTzInTaskList(user, posts);
        return posts;
    }

    @Override
    public List<Post> findByStatus(Boolean status) {
        return postRepository.findByStatus(status);
    }

    private void setUserTzInTaskList(User user, List<Post> posts) {
        for (Post post : posts) {
            String userZoneId = Objects.requireNonNull(user).getTimezone() != null
                    ? user.getTimezone() : String.valueOf(ZoneId.systemDefault());
            post.setCreated(getUserLocalDateTime(post.getCreated(), userZoneId));
        }
    }

    private LocalDateTime getUserLocalDateTime(LocalDateTime localDateTime, String userZoneId) {
        return localDateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of(userZoneId))
                .toLocalDateTime();
    }

    private Map<String, ?> getParams(HttpServletRequest req, HttpSession httpSession) throws Exception {
        int number;
        try {
            number = Integer.parseInt(req.getParameter("engine_number"));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("В номере двигателя могут быть только цифры. Данные не сохранены");
        }
        Engine engine = Engine.builder()
                .name(req.getParameter("engine_name").toUpperCase(Locale.ROOT))
                .number(number)
                .build();
        Driver driver = Driver.builder()
                .name(req.getParameter("driver_name"))
                .build();
        Car car = Car.builder()
                .name(req.getParameter("car_name").toUpperCase(Locale.ROOT))
                .engine(engine)
                .driver(driver)
                .build();
        Post post = Post.builder()
                .description(req.getParameter("post_desc"))
                .car(car)
                .user((User) httpSession.getAttribute("user"))
                .photo(req.getPart("attachment").getInputStream().readAllBytes())
                .build();
        return Map.of("engine", engine, "driver", driver, "car", car, "post", post);
    }
}
