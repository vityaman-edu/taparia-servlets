package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Color;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Ellipse;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Figure;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public final class EllipseView extends FigureView {
    @JsonProperty("color") private final String color;
    @JsonProperty("center") private final VectorView center;
    @JsonProperty("radius") private final VectorView radius;

    public EllipseView(
        String color,
        VectorView center,
        VectorView radius
    ) {
        super("ellipse");
        this.color = color;
        this.center = center;
        this.radius = radius;
    }

    public static EllipseView fromModel(Ellipse model) {
        return new EllipseView(
            model.color().hex(),
            VectorView.fromModel(model.center()),
            new VectorView(model.radius().x(), model.radius().y())
        );
    }

    @Override
    public Figure model() {
        return new Ellipse(
            Color.parse(color),
            center.model(),
            new Ellipse.Radius(
                (long) radius.x(),
                (long) radius.y()
            )
        );
    }
}
