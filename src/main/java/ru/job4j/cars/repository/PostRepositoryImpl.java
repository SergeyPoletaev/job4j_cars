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
public class PostRepositoryImpl implements PostRepository {
    private static final String FROM_POST_WHERE_ID = "from Post where id= :id";
    private static final String FROM_POST = "from Post";
    private static final String FROM_POST_WHERE_CREATED_CURRENT_DATE = "from Post where created >= :localDateTime";
    private static final String FROM_POST_WHERE_CAR_NAME = "from Post where car = (from Car where name= :model)";
    private static final String FROM_POST_WITH_PHOTO = "from Post where photo is not null";
    private CrudRepository crudRepository;

    @Override
    public Post create(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.update(post));
    }

    @Override
    public void delete(int id) {
        Post post = new Post();
        post.setId(id);
        crudRepository.run(session -> session.delete(post));
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(FROM_POST_WHERE_ID, Post.class, Map.of("id", id));
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query(FROM_POST, Post.class);
    }

    @Override
    public List<Post> findPostsForCurrentDay() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        return crudRepository.query(FROM_POST_WHERE_CREATED_CURRENT_DATE, Post.class, Map.of("localDateTime", localDateTime));
    }

    @Override
    public List<Post> findPostsByCarModel(String model) {
        return crudRepository.query(FROM_POST_WHERE_CAR_NAME, Post.class, Map.of("model", model));
    }

    @Override
    public List<Post> findPostsWithPhoto() {
        return crudRepository.query(FROM_POST_WITH_PHOTO, Post.class);
    }
}
