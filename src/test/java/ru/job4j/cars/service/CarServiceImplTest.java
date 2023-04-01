package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CarServiceImplTest {

    @Test
    void whenCreateThenProxy() {
        CarRepository carRepository = mock(CarRepository.class);
        CarService carService = new CarServiceImpl(carRepository);
        Car car = new Car();
        carService.create(car);
        verify(carRepository).create(car);
    }

    @Test
    void whenUpdateThenProxy() {
        CarRepository carRepository = mock(CarRepository.class);
        CarService carService = new CarServiceImpl(carRepository);
        Car car = new Car();
        carService.update(car);
        verify(carRepository).update(car);
    }

    @Test
    void whenDeleteThenProxy() {
        CarRepository carRepository = mock(CarRepository.class);
        CarService carService = new CarServiceImpl(carRepository);
        int id = 1;
        carService.delete(id);
        verify(carRepository).delete(id);
    }

    @Test
    @DisabledIfSystemProperty(named = "ci", matches = "true")
    void whenFindByIdThenProxy() {
        CarRepository carRepository = mock(CarRepository.class);
        CarService carService = new CarServiceImpl(carRepository);
        int id = 1;
        carService.findById(id);
        verify(carRepository).findById(id);
    }

    @Test
    void whenFindAllThenProxy() {
        CarRepository carRepository = mock(CarRepository.class);
        CarService carService = new CarServiceImpl(carRepository);
        carService.findAll();
        verify(carRepository).findAll();
    }
}