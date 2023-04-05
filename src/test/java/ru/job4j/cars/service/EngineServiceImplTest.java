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
}