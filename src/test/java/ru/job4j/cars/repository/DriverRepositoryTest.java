package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.Driver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DriverRepositoryTest {
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
    void whenCreateDriverThenFindThisDriver() {
        Driver driver = new Driver();
        driver.setName("anna");
        DriverRepository driverRepository = new DriverRepository(new CrudRepositoryImpl(sf));
        driverRepository.create(driver);
        assertThat(driverRepository.findById(driver.getId()).orElseThrow().getName())
                .isEqualTo("anna");
    }

    @Test
    void whenUpdateDriverThenUpdatedDriver() {
        Driver driver = new Driver();
        driver.setName("anna");
        DriverRepository driverRepository = new DriverRepository(new CrudRepositoryImpl(sf));
        driverRepository.create(driver);
        driver.setName("sveta");
        driverRepository.update(driver);
        assertThat(driverRepository.findById(driver.getId()).orElseThrow().getName())
                .isEqualTo("sveta");
    }

    @Test
    void whenDeleteThenThisDriverNotFound() {
        Driver driver = new Driver();
        driver.setName("anna");
        DriverRepository driverRepository = new DriverRepository(new CrudRepositoryImpl(sf));
        driverRepository.create(driver);
        driverRepository.delete(driver.getId());
        assertThat(driverRepository.findById(driver.getId())).isEmpty();
    }

    @Test
    void whenFindAllThenReturnAllDriversList() {
        Driver driver1 = new Driver();
        driver1.setName("anna");
        Driver driver2 = new Driver();
        driver2.setName("sveta");
        DriverRepository driverRepository = new DriverRepository(new CrudRepositoryImpl(sf));
        driverRepository.create(driver1);
        driverRepository.create(driver2);
        assertThat(driverRepository.findAll()).isEqualTo(List.of(driver1, driver2));
    }
}