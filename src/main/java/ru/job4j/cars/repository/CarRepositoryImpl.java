package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CarRepositoryImpl implements CarRepository {
    private static final String FROM_CAR_WHERE_ID = "from Car where id= :id";
    private static final String FROM_CAR = "from Car";
    private CrudRepository crudRepository;

    @Override
    public Car create(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.update(car));
    }

    @Override
    public void delete(int id) {
        Car car = new Car();
        car.setId(id);
        crudRepository.run(session -> session.delete(car));
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(FROM_CAR_WHERE_ID, Car.class, Map.of("id", id));
    }

    @Override
    public List<Car> findAll() {
        return crudRepository.query(FROM_CAR, Car.class);
    }
}
