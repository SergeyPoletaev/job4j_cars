package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EngineServiceImplTest {

    @Test
    void whenCreateSuccess() {
        EngineRepository engineRepository = mock(EngineRepository.class);
        EngineService engineService = new EngineServiceImpl(engineRepository);
        Engine engine = new Engine();
        Engine engineDb = new Engine();
        engineDb.setId(1);
        when(engineRepository.create(engine)).thenReturn(engineDb);
        Engine rsl = engineService.create(engine);
        verify(engineRepository).create(engine);
        assertThat(rsl).isEqualTo(engine);
    }

    @Test
    void whenCreateFail() {
        EngineRepository engineRepository = mock(EngineRepository.class);
        EngineService engineService = new EngineServiceImpl(engineRepository);
        Engine engine = new Engine();
        when(engineRepository.create(engine)).thenReturn(engine);
        Throwable thrown = assertThrows(IllegalStateException.class, () -> engineService.create(engine));
        verify(engineRepository).create(engine);
        assertThat(thrown.getMessage()).isEqualTo("При сохранении данных произошла ошибка");
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