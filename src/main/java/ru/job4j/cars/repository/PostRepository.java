package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class PostRepository {
    private static final String FROM_POST_WHERE_ID = "from auto_post where id= :id";
    private static final String FROM_POST = "from auto_post";
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
}
