package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import ru.vityaman.itmo.web.lab.servlets.backend.model.Hashable;

public abstract class Figure implements Hashable {

    private final String type;

    protected Figure(String type) {
        this.type = type;
    }

    public abstract boolean contains(Point point);

    public final String type() {
        return type;
    }
}
