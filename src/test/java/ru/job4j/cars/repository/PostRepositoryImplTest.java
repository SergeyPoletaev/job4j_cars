package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryImplTest {
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
            session.createQuery("delete from auto_post").executeUpdate();
            session.createQuery("delete from auto_user").executeUpdate();
            session.createQuery("delete from car").executeUpdate();
            session.createQuery("delete from engine").executeUpdate();
            session.createQuery("delete from driver").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    @Test
    void whenCreateThenFindThisPost() {
        Post post = new Post();
        post.setCreated(LocalDateTime.now());
        post.setDescription("test");
        Map<User, Car> preconditions = ensurePreconditions();
        post.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post.setCar(preconditions.values().stream().findFirst().orElseThrow());
        PostRepository postRepository = new PostRepositoryImpl(new CrudRepositoryImpl(sf));
        postRepository.create(post);
        assertThat(postRepository.findById(post.getId()).orElseThrow().getDescription())
                .isEqualTo("test");
    }

    @Test
    void whenUpdateThenFindUpdatedPost() {
        Post post = new Post();
        post.setCreated(LocalDateTime.now());
        post.setDescription("test");
        Map<User, Car> preconditions = ensurePreconditions();
        post.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post.setCar(preconditions.values().stream().findFirst().orElseThrow());
        PostRepository postRepository = new PostRepositoryImpl(new CrudRepositoryImpl(sf));
        postRepository.create(post);
        post.setDescription("test1");
        postRepository.update(post);
        assertThat(postRepository.findById(post.getId()).orElseThrow().getDescription())
                .isEqualTo("test1");
    }

    @Test
    void whenDeleteThenThisPostNotFound() {
        Post post = new Post();
        post.setCreated(LocalDateTime.now());
        post.setDescription("test");
        Map<User, Car> preconditions = ensurePreconditions();
        post.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post.setCar(preconditions.values().stream().findFirst().orElseThrow());
        PostRepository postRepository = new PostRepositoryImpl(new CrudRepositoryImpl(sf));
        postRepository.create(post);
        postRepository.delete(post.getId());
        assertThat(postRepository.findById(post.getId())).isEmpty();
    }

    @Test
    void whenFindAllThenFindAllPostsList() {
        Map<User, Car> preconditions = ensurePreconditions();
        Post post1 = new Post();
        post1.setCreated(LocalDateTime.now());
        post1.setDescription("test");
        post1.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post1.setCar(preconditions.values().stream().findFirst().orElseThrow());
        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now());
        post2.setDescription("test2");
        post2.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post2.setCar(preconditions.values().stream().findFirst().orElseThrow());
        PostRepository postRepository = new PostRepositoryImpl(new CrudRepositoryImpl(sf));
        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findAll()).isEqualTo(List.of(post1, post2));
    }

    @Test
    void whenFindPostsForCurrentDayThenFindListPostsForCurrentDay() {
        Map<User, Car> preconditions = ensurePreconditions();
        Post post1 = new Post();
        post1.setCreated(LocalDateTime.now());
        post1.setDescription("test");
        post1.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post1.setCar(preconditions.values().stream().findFirst().orElseThrow());
        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now().minusDays(1));
        post2.setDescription("test2");
        post2.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post2.setCar(preconditions.values().stream().findFirst().orElseThrow());
        PostRepository postRepository = new PostRepositoryImpl(new CrudRepositoryImpl(sf));
        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findPostsForCurrentDay()).isEqualTo(List.of(post1));
    }

    @Test
    void whenFindPostsByCarModelThenFindPostWhitThisCarModelList() {
        Post post1 = new Post();
        post1.setCreated(LocalDateTime.now());
        post1.setDescription("test");
        Map<User, Car> preconditions1 = ensurePreconditions();
        post1.setUser(preconditions1.keySet().stream().findFirst().orElseThrow());
        post1.setCar(preconditions1.values().stream().findFirst().orElseThrow());
        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now().minusDays(1));
        post2.setDescription("test2");
        Map<User, Car> preconditions2 = ensurePreconditions();
        post2.setUser(preconditions2.keySet().stream().findFirst().orElseThrow());
        post2.setCar(preconditions2.values().stream().findFirst().orElseThrow());
        PostRepository postRepository = new PostRepositoryImpl(new CrudRepositoryImpl(sf));
        postRepository.create(post1);
        postRepository.create(post2);
        String carName = postRepository.findById(post1.getId()).orElseThrow().getCar().getName();
        assertThat(postRepository.findPostsByCarModel(carName)).isEqualTo(List.of(post1));
    }

    @Test
    void whenFindPostsWithPhotoThenFindPostWithPhotoList() {
        Map<User, Car> preconditions = ensurePreconditions();
        Post post1 = new Post();
        post1.setCreated(LocalDateTime.now());
        post1.setDescription("test");
        post1.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post1.setCar(preconditions.values().stream().findFirst().orElseThrow());
        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now().minusDays(1));
        post2.setDescription("test2");
        post2.setPhoto(new byte[1]);
        post2.setUser(preconditions.keySet().stream().findFirst().orElseThrow());
        post2.setCar(preconditions.values().stream().findFirst().orElseThrow());
        PostRepository postRepository = new PostRepositoryImpl(new CrudRepositoryImpl(sf));
        postRepository.create(post1);
        postRepository.create(post2);
        assertThat(postRepository.findPostsWithPhoto()).isEqualTo(List.of(post2));
    }

    private Map<User, Car> ensurePreconditions() {
        User user = new User();
        user.setLogin("test" + new Random().nextInt());
        user.setPassword("123");
        UserRepository userRepository = new UserRepositoryImpl(new CrudRepositoryImpl(sf));
        userRepository.create(user);
        Driver driver = new Driver();
        driver.setName("driverName");
        DriverRepository driverRepository = new DriverRepositoryImpl(new CrudRepositoryImpl(sf));
        driverRepository.create(driver);
        Engine engine = new Engine();
        engine.setName("engineName");
        engine.setNumber(123);
        EngineRepository engineRepository = new EngineRepositoryImpl(new CrudRepositoryImpl(sf));
        engineRepository.create(engine);
        Car car = new Car();
        car.setName("carName" + new Random().nextInt());
        car.setDriver(driver);
        car.setEngine(engine);
        CarRepository carRepository = new CarRepositoryImpl(new CrudRepositoryImpl(sf));
        carRepository.create(car);
        return Map.of(user, car);
    }
}