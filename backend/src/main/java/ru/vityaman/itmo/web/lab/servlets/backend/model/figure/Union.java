package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import java.util.List;

public final class Union extends FigureAggregator {
    public Union(List<? extends Figure> children) {
        super("union", children);
    }

    @Override
    public boolean contains(Point point) {
        return children().stream().anyMatch(child -> child.contains(point));
    }
}
