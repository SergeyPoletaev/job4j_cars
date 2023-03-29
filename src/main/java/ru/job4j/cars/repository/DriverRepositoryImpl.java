package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class DriverRepositoryImpl implements DriverRepository {
    private static final String FROM_DRIVER_WHERE_ID = "from Driver where id= :id";
    private static final String FROM_DRIVER = "from Driver";
    private CrudRepository crudRepository;

    @Override
    public Driver create(Driver driver) {
        crudRepository.run(session -> session.save(driver));
        return driver;
    }

    @Override
    public void update(Driver driver) {
        crudRepository.run(session -> session.update(driver));
    }

    @Override
    public void delete(int id) {
        Driver driver = new Driver();
        driver.setId(id);
        crudRepository.run(session -> session.delete(driver));
    }

    @Override
    public Optional<Driver> findById(int id) {
        return crudRepository.optional(FROM_DRIVER_WHERE_ID, Driver.class, Map.of("id", id));
    }

    @Override
    public List<Driver> findAll() {
        return crudRepository.query(FROM_DRIVER, Driver.class);
    }
}
