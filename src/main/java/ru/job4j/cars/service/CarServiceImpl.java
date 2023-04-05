package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car create(Car car) {
        if (carRepository.create(car).getId() == 0) {
            throw new IllegalStateException("При сохранении данных произошла ошибка");
        }
        return car;
    }
}
