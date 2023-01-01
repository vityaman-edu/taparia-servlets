package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import lombok.Value;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Hashable;

import static java.lang.Math.pow;

@Value
public final class Ellipse extends TrivialFigure {
    private final Point center;
    private final Radius radius;

    public Ellipse(Color color, Point center, Radius radius) {
        super("ellipse", color);
        this.center = center;
        this.radius = radius;
    }

    public static Ellipse circle(Color color, Point center, long radius) {
        return new Ellipse(color, center, new Radius(radius, radius));
    }

    @Override
    public boolean contains(Point point) {
        long dx = point.x() - center.x();
        long dy = point.y() - center.y();
        return pow(dx, 2) / pow(radius.x(), 2)
            + pow(dy, 2) / pow(radius.y(), 2) <= 1;
    }

    @Override
    public String hash() {
        return Hashable.hash(
            String.format(
                "ellipse:%s:%s:%s:%s",
                center.x(), center.y(),
                radius.x, radius.y
            )
        );
    }

    @Value
    public static final class Radius {
        private final long x;
        private final long y;
    }
}

