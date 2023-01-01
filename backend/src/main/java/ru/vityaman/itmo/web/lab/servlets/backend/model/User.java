package ru.vityaman.itmo.web.lab.servlets.backend.model;

import lombok.Value;

@Value
public final class User {
    private final String id;

    public static User withId(String id) {
        return new User(id);
    }
}
