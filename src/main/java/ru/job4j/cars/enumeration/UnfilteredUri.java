package ru.job4j.cars.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UnfilteredUri {
    LOGIN_PAGE("/user/login"),
    REGISTRATION("/user/registration"),
    ADD_FORM("/user/add");

    private final String value;
}
