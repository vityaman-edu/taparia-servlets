package ru.vityaman.itmo.web.lab.servlets.backend.model.figure;

public final class Color {
    private static final int MAX = 255;
    private static final int MIN = 0;

    private final int alpha;
    private final int red;
    private final int green;
    private final int blue;

    public Color(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }


    public String hex() {
        return String.format(
            "#%02x%02x%02x",
            red,
            green,
            blue
        );
    }

    public static Color parse(String color) {
        return Color.black();
    }

    public static Color rgb(int red, int green, int blue) {
        return new Color(MAX, red, green, blue);
    }

    public static Color white() {
        return rgb(MAX, MAX, MAX);
    }

    public static Color black() {
        return rgb(MIN, MIN, MIN);
    }

    public static Color transparent() {
        return new Color(MIN, MIN, MIN, MIN);
    }
}
