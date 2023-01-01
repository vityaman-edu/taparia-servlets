package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import lombok.Value;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Hashable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public final class Polygon extends TrivialFigure {
    private static final long FAIREST_POINT = 999L;
    private static final long SHIFT = 10L;

    private final List<Point> points;

    public Polygon(Color color, List<Point> points) {
        super("polygon", color);
        this.points = points;
    }

    public static Polygon rectangle(
        Color color, Point leftTop, Point rightBottom
    ) {
        return new Polygon(
            color,
            List.of(
                leftTop,
                Point.of(rightBottom.x(), leftTop.y()),
                rightBottom,
                Point.of(leftTop.x(), rightBottom.y())
            )
        );
    }

    @Override
    public boolean contains(Point point) {
        long fairestPoint = points.stream()
            .flatMap(p -> Stream.of(p.x(), p.y()))
            .max(Comparator.comparingLong(Math::abs))
            .orElse(FAIREST_POINT);
        Point corner = Point.of(fairestPoint, fairestPoint + SHIFT);
        Segment ray = new Segment(point, corner);
        return sides().stream()
            .filter(side -> side.intersects(ray))
            .count() % 2 == 1;
    }

    private List<Segment> sides() {
        int pointsCount = points.size();
        List<Segment> result = new ArrayList<>(pointsCount);
        for (int i = 1; i <= pointsCount; i++) {
            result.add(new Segment(
                points.get(i - 1),
                points.get(i % pointsCount))
            );
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public String hash() {
        return Hashable.hash(
            points.stream()
                .map(p -> p.x() + ":" + p.y())
                .collect(Collectors.joining(":", "polygon", ""))
        );
    }
}
