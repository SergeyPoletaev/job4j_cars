package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Test
    void whenCreateSuccess() {
        CarRepository carRepository = mock(CarRepository.class);
        CarService carService = new CarServiceImpl(carRepository);
        Car car = new Car();
        Car carDb = new Car();
        carDb.setId(1);
        when(carRepository.create(car)).thenReturn(carDb);
        Car rsl = carService.create(car);
        verify(carRepository).create(car);
        assertThat(rsl).isEqualTo(car);
    }

    @Test
    void whenCreateFail() {
        CarRepository carRepository = mock(CarRepository.class);
        CarService carService = new CarServiceImpl(carRepository);
        Car car = new Car();
        when(carRepository.create(car)).thenReturn(car);
        Throwable thrown = assertThrows(IllegalStateException.class, () -> carService.create(car));
        verify(carRepository).create(car);
        assertThat(thrown.getMessage()).isEqualTo("При сохранении данных произошла ошибка");
    }
}