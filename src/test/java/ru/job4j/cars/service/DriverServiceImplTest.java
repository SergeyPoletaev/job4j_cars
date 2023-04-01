package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.repository.DriverRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DriverServiceImplTest {

    @Test
    void whenCreateThenProxy() {
        DriverRepository driverRepository = mock(DriverRepository.class);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        Driver driver = new Driver();
        driverService.create(driver);
        verify(driverRepository).create(driver);
    }

    @Test
    void whenUpdateThenProxy() {
        DriverRepository driverRepository = mock(DriverRepository.class);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        Driver driver = new Driver();
        driverService.update(driver);
        verify(driverRepository).update(driver);
    }

    @Test
    void whenDeleteThenProxy() {
        DriverRepository driverRepository = mock(DriverRepository.class);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        int id = 1;
        driverService.delete(id);
        verify(driverRepository).delete(id);
    }

    @Test
    void whenFindByIdThenProxy() {
        DriverRepository driverRepository = mock(DriverRepository.class);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        int id = 1;
        driverService.findById(id);
        verify(driverRepository).findById(id);
    }

    @Test
    void whenFindAllThenProxy() {
        DriverRepository driverRepository = mock(DriverRepository.class);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        driverService.findAll();
        verify(driverRepository).findAll();
    }
}