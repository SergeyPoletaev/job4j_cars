package ru.job4j.cars.service;

import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.repository.DriverRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DriverServiceImplTest {

    @Test
    void whenCreateSuccess() {
        DriverRepository driverRepository = mock(DriverRepository.class);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        Driver driver = new Driver();
        Driver driverDb = new Driver();
        driverDb.setId(1);
        when(driverRepository.create(driver)).thenReturn(driverDb);
        Driver rsl = driverService.create(driver);
        verify(driverRepository).create(driver);
        assertThat(rsl).isEqualTo(driver);
    }

    @Test
    void whenCreateFail() {
        DriverRepository driverRepository = mock(DriverRepository.class);
        DriverService driverService = new DriverServiceImpl(driverRepository);
        Driver driver = new Driver();
        when(driverRepository.create(driver)).thenReturn(driver);
        Throwable thrown = assertThrows(IllegalStateException.class, () -> driverService.create(driver));
        verify(driverRepository).create(driver);
        assertThat(thrown.getMessage()).isEqualTo("При сохранении данных произошла ошибка");
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