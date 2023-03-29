package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class EngineRepositoryImpl implements EngineRepository {
    private static final String FROM_ENGINE_WHERE_ID = "from engine where id= :id";
    private static final String FROM_ENGINE = "from engine";
    private CrudRepository crudRepository;

    @Override
    public Engine create(Engine engine) {
        crudRepository.run(session -> session.save(engine));
        return engine;
    }

    @Override
    public void update(Engine engine) {
        crudRepository.run(session -> session.update(engine));
    }

    @Override
    public void delete(int id) {
        Engine engine = new Engine();
        engine.setId(id);
        crudRepository.run(session -> session.delete(engine));
    }

    @Override
    public Optional<Engine> findById(int id) {
        return crudRepository.optional(FROM_ENGINE_WHERE_ID, Engine.class, Map.of("id", id));
    }

    @Override
    public List<Engine> findAll() {
        return crudRepository.query(FROM_ENGINE, Engine.class);
    }
}
