package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EngineServiceImplTest {

    @Test
    void whenCreateThenProxy() {
        EngineRepository engineRepository = mock(EngineRepository.class);
        EngineService engineService = new EngineServiceImpl(engineRepository);
        Engine engine = new Engine();
        engineService.create(engine);
        verify(engineRepository).create(engine);
    }

    @Test
    void whenUpdateThenProxy() {
        EngineRepository engineRepository = mock(EngineRepository.class);
        EngineService engineService = new EngineServiceImpl(engineRepository);
        Engine engine = new Engine();
        engineService.update(engine);
        verify(engineRepository).update(engine);
    }

    @Test
    void whenDeleteThenProxy() {
        EngineRepository engineRepository = mock(EngineRepository.class);
        EngineService engineService = new EngineServiceImpl(engineRepository);
        int id = 1;
        engineService.delete(id);
        verify(engineRepository).delete(id);
    }

    @Test
    void whenFindByIdThenProxy() {
        EngineRepository engineRepository = mock(EngineRepository.class);
        EngineService engineService = new EngineServiceImpl(engineRepository);
        int id = 1;
        engineService.findById(id);
        verify(engineRepository).findById(id);
    }

    @Test
    void whenFindAllThenProxy() {
        EngineRepository engineRepository = mock(EngineRepository.class);
        EngineService engineService = new EngineServiceImpl(engineRepository);
        engineService.findAll();
        verify(engineRepository).findAll();
    }
}