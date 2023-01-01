package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import ru.vityaman.itmo.web.lab.servlets.backend.model.Hashable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FigureAggregator extends Figure {
    private final List<? extends Figure> children;

    FigureAggregator(String type, List<? extends Figure> children) {
        super(type);
        this.children = children;
    }

    public final List<? extends Figure> children() {
        return children;
    }

    @Override
    public final String hash() {
        return Hashable.hash(
            children().stream()
                .map(Hashable::hash)
                .collect(Collectors.joining(":", type(), ""))
        );
    }
}
