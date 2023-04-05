package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

@Service
@AllArgsConstructor
public class EngineServiceImpl implements EngineService {
    private EngineRepository engineRepository;

    @Override
    public Engine create(Engine engine) {
        if (engineRepository.create(engine).getId() == 0) {
            throw new IllegalStateException("При сохранении данных произошла ошибка");
        }
        return engine;
    }
}
