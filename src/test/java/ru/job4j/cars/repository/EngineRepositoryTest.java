package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EngineRepositoryTest {
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
            session.createQuery("delete from engine").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Test
    void whenCreateThenFindThisEngine() {
        Engine engine = new Engine();
        engine.setName("test");
        engine.setNumber(123);
        EngineRepository engineRepository = new EngineRepository(new CrudRepositoryImpl(sf));
        engineRepository.create(engine);
        assertThat(engineRepository.findById(engine.getId()).orElseThrow().getName()).isEqualTo("test");
    }

    @Test
    void whenUpdateThenFindUpdatedEngine() {
        Engine engine = new Engine();
        engine.setName("test");
        engine.setNumber(123);
        EngineRepository engineRepository = new EngineRepository(new CrudRepositoryImpl(sf));
        engineRepository.create(engine);
        engine.setName("test2");
        engineRepository.update(engine);
        assertThat(engineRepository.findById(engine.getId()).orElseThrow().getName()).isEqualTo("test2");
    }

    @Test
    void whenDeleteThenThisEngineNotFound() {
        Engine engine = new Engine();
        engine.setName("test");
        engine.setNumber(123);
        EngineRepository engineRepository = new EngineRepository(new CrudRepositoryImpl(sf));
        engineRepository.create(engine);
        engineRepository.delete(engine.getId());
        assertThat(engineRepository.findById(engine.getId())).isEmpty();
    }

    @Test
    void thenFindAllThenAllEnginesListFound() {
        Engine engine1 = new Engine();
        engine1.setName("test1");
        engine1.setNumber(123);
        Engine engine2 = new Engine();
        engine2.setName("test2");
        engine2.setNumber(456);
        EngineRepository engineRepository = new EngineRepository(new CrudRepositoryImpl(sf));
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        assertThat(engineRepository.findAll()).isEqualTo(List.of(engine1, engine2));
    }
}