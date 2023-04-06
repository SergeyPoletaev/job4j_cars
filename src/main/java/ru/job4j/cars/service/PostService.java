package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {

    Post create(Post post);

    void update(Post post, HttpSession httpSession);

    Post findById(int id);

    List<Post> findAll(User user);

    List<Post> findByStatus(Boolean status);

    Post createPostFromRequestParam(HttpServletRequest req, HttpSession httpSession) throws Exception;

}
