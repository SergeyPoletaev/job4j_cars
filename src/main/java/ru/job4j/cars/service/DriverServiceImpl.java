package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

    @Override
    public Driver create(Driver driver) {
        if (driverRepository.create(driver).getId() == 0) {
            throw new IllegalStateException("При сохранении данных произошла ошибка");
        }
        return driver;
    }

    @Override
    public void update(Driver driver) {
        driverRepository.update(driver);
    }

    @Override
    public void delete(int id) {
        driverRepository.delete(id);
    }

    @Override
    public Optional<Driver> findById(int id) {
        return driverRepository.findById(id);
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }
}
