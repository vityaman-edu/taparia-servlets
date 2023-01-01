package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

public abstract class TrivialFigure extends Figure {
    private final Color color;

    TrivialFigure(String type, Color color) {
        super(type);
        this.color = color;
    }

    public final Color color() {
        return color;
    }
}
