package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import lombok.Getter;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Hashable;

public final class Negative extends Figure {
    @Getter private final Figure child;

    public Negative(Figure child) {
        super("negative");
        this.child = child;
    }

    @Override
    public boolean contains(Point point) {
        return !child.contains(point);
    }

    @Override
    public String hash() {
        return Hashable.hash("negative" + child.hash());
    }
}
