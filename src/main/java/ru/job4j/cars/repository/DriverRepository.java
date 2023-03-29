package ru.job4j.cars.repository;

import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverRepository {

    Driver create(Driver driver);

    void update(Driver driver);

    void delete(int id);

    Optional<Driver> findById(int id);

    List<Driver> findAll();
}
