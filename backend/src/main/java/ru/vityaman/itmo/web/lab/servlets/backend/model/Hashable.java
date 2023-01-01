package ru.vityaman.itmo.web.lab.servlets.backend.model;

public interface Hashable {
    String hash();

    static String hash(String string) {
        return string;
    }
}
