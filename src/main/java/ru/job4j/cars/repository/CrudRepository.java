package ru.job4j.cars.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface CrudRepository {

    void run(Consumer<Session> command);

    void run(String query, Map<String, Object> args);

    <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args);

    <T> List<T> query(String query, Class<T> cl);

    <T> List<T> query(String query, Class<T> cl, Map<String, Object> args);
}
