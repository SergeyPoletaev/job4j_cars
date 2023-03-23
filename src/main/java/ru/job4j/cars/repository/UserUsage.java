package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;

public class UserUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try (SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory()) {
            var userRepository = new UserRepository(new CrudRepositoryImpl(sf));
            var user = new User();
            user.setLogin("admin5");
            user.setPassword("admin5");
            userRepository.create(user);
            userRepository.findAllOrderById().forEach(System.out::println);
            System.out.println("1####################################");
            userRepository.findByLikeLogin("d").forEach(System.out::println);
            System.out.println("2####################################");
            userRepository.findById(user.getId()).ifPresent(System.out::println);
            System.out.println("3####################################");
            userRepository.findByLogin("admin5").ifPresent(System.out::println);
            System.out.println("4####################################");
            user.setPassword("password");
            userRepository.update(user);
            userRepository.findById(user.getId()).ifPresent(System.out::println);
            System.out.println("5####################################");
            userRepository.delete(user.getId());
            userRepository.findAllOrderById().forEach(System.out::println);
            System.out.println("6####################################");
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
