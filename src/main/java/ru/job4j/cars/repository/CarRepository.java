package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CarRepository {
    private static final String FROM_CAR_WHERE_ID = "from car where id= :id";
    private static final String FROM_CAR = "from car";
    private CrudRepository crudRepository;

    public Car create(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    public void update(Car car) {
        crudRepository.run(session -> session.update(car));
    }

    public void delete(int id) {
        Car car = new Car();
        car.setId(id);
        crudRepository.run(session -> session.delete(car));
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(FROM_CAR_WHERE_ID, Car.class, Map.of("id", id));
    }

    public List<Car> findAll() {
        return crudRepository.query(FROM_CAR, Car.class);
    }
}
