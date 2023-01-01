package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

import lombok.Value;

@Value
public final class Point {
    private final long x;
    private final long y;

    public static Point of(long x, long y) {
        return new Point(x, y);
    }
}
