package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class DriverRepository {
    private static final String FROM_DRIVER_WHERE_ID = "from driver where id= :id";
    private static final String FROM_DRIVER = "from driver";
    private CrudRepository crudRepository;

    public Driver create(Driver driver) {
        crudRepository.run(session -> session.save(driver));
        return driver;
    }

    public void update(Driver driver) {
        crudRepository.run(session -> session.update(driver));
    }

    public void delete(int id) {
        Driver driver = new Driver();
        driver.setId(id);
        crudRepository.run(session -> session.delete(driver));
    }

    public Optional<Driver> findById(int id) {
        return crudRepository.optional(FROM_DRIVER_WHERE_ID, Driver.class, Map.of("id", id));
    }

    public List<Driver> findAll() {
        return crudRepository.query(FROM_DRIVER, Driver.class);
    }
}
