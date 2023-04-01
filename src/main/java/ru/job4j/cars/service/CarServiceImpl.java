package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public void update(Car car) {
        carRepository.update(car);
    }

    @Override
    public void delete(int id) {
        carRepository.delete(id);
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
