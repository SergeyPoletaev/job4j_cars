package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    @Test
    void getIndex() {
        assertThat(new IndexController().getIndex()).isEqualTo("redirect:/car/cars");
    }
}