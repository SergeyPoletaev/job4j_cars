package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepositoryTest {
    private static SessionFactory sf;

    @BeforeAll
    static void init() {
        sf = new HbmTestConfig().getSessionFactory();
    }

    @AfterAll
    static void close() {
        sf.close();
    }

    @AfterEach
    void cleanDb() {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from car").executeUpdate();
            session.createQuery("delete from engine").executeUpdate();
            session.createQuery("delete from driver").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    @Test
    void whenCreateCarThenFindThisCar() {
        CarRepository carRepository = new CarRepository(new CrudRepositoryImpl(sf));
        Car car = getCar();
        carRepository.create(car);
        assertThat(carRepository.findById(car.getId()).orElseThrow().getName())
                .isEqualTo("testCar");
    }

    @Test
    void whenUpdateCarThenFindUpdatedCar() {
        CarRepository carRepository = new CarRepository(new CrudRepositoryImpl(sf));
        Car car = getCar();
        carRepository.create(car);
        car.setName("testCar2");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).orElseThrow().getName())
                .isEqualTo("testCar2");
    }

    @Test
    void whenDeleteThenThisCarNotFound() {
        CarRepository carRepository = new CarRepository(new CrudRepositoryImpl(sf));
        Car car = getCar();
        carRepository.create(car);
        carRepository.delete(car.getId());
        assertThat(carRepository.findById(car.getId())).isEmpty();
    }

    @Test
    void whenFindAllThenFindAllCars() {
        CarRepository carRepository = new CarRepository(new CrudRepositoryImpl(sf));
        Car car1 = getCar();
        Car car2 = getCar();
        carRepository.create(car1);
        carRepository.create(car2);
        assertThat(carRepository.findAll()).isEqualTo(List.of(car1, car2));
    }

    private Car getCar() {
        Engine engine = new Engine();
        engine.setName("xwq");
        engine.setNumber(123);
        EngineRepository engineRepository = new EngineRepository(new CrudRepositoryImpl(sf));
        engineRepository.create(engine);
        Driver driver = new Driver();
        driver.setName("anna");
        DriverRepository driverRepository = new DriverRepository(new CrudRepositoryImpl(sf));
        driverRepository.create(driver);
        Car car = new Car();
        car.setName("testCar");
        car.setEngine(engine);
        car.setDriver(driver);
        return car;
    }
}