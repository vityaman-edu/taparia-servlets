package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import java.util.List;

public final class Intersection extends FigureAggregator {
    public Intersection(List<Figure> children) {
        super("intersection", children);
    }

    @Override
    public boolean contains(Point point) {
        return children().stream().allMatch(child -> child.contains(point));
    }
}
