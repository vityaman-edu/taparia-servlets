package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Color;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Figure;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Polygon;

import java.util.List;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public final class PolygonView extends FigureView {
    @JsonProperty("color") private final String color;
    @JsonProperty("points") private final List<VectorView> points;

    public PolygonView(String color, List<VectorView> points) {
        super("polygon");
        this.color = color;
        this.points = points;
    }

    public static FigureView fromModel(Polygon model) {
        return new PolygonView(
            model.color().hex(),
            model.points().stream().map(VectorView::fromModel).toList()
        );
    }

    @Override
    public Figure model() {
        return new Polygon(
            Color.parse(color),
            points.stream().map(VectorView::model).toList()
        );
    }
}
