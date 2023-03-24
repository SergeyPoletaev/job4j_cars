package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class PostRepository {
    private static final String FROM_POST_WHERE_ID = "from auto_post where id= :id";
    private static final String FROM_POST = "from auto_post";
    private static final String FROM_POST_WHERE_CREATED_CURRENT_DATE = "from auto_post where created >= :localDateTime";
    private static final String FROM_POST_WHERE_CAR_NAME = "from auto_post where car = (from car where name= :model)";
    private static final String FROM_POST_WITH_PHOTO = "from auto_post where photo is not null";
    private CrudRepository crudRepository;

    public Post create(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    public void update(Post post) {
        crudRepository.run(session -> session.update(post));
    }

    public void delete(int id) {
        Post post = new Post();
        post.setId(id);
        crudRepository.run(session -> session.delete(post));
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(FROM_POST_WHERE_ID, Post.class, Map.of("id", id));
    }

    public List<Post> findAll() {
        return crudRepository.query(FROM_POST, Post.class);
    }

    public List<Post> findPostsForCurrentDay() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        return crudRepository.query(FROM_POST_WHERE_CREATED_CURRENT_DATE, Post.class, Map.of("localDateTime", localDateTime));
    }

    public List<Post> findPostsByCarModel(String model) {
        return crudRepository.query(FROM_POST_WHERE_CAR_NAME, Post.class, Map.of("model", model));
    }

    public List<Post> findPostsWithPhoto() {
        return crudRepository.query(FROM_POST_WITH_PHOTO, Post.class);
    }
}
