package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Ellipse;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Figure;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Intersection;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Negative;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Polygon;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Union;

@Value
@NonFinal
@SuperBuilder
@RequiredArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PolygonView.class, name = "polygon"),
    @JsonSubTypes.Type(value = EllipseView.class, name = "ellipse"),
    @JsonSubTypes.Type(value = UnionView.class, name = "union"),
    @JsonSubTypes.Type(value = IntersectionView.class, name = "intersection"),
    @JsonSubTypes.Type(value = NegativeView.class, name = "negative"),
})
public abstract class FigureView implements View {
    @JsonProperty("type") private final String type;

    public abstract Figure model();

    public static FigureView fromModelFigure(Figure model) {
        Class<? extends Figure> klass = model.getClass();
        if (klass.equals(Ellipse.class)) {
            return EllipseView.fromModel((Ellipse) model);
        }
        if (klass.equals(Intersection.class)) {
            return IntersectionView.fromModel((Intersection) model);
        }
        if (klass.equals(Negative.class)) {
            return NegativeView.fromModel((Negative) model);
        }
        if (klass.equals(Polygon.class)) {
            return PolygonView.fromModel((Polygon) model);
        }
        if (klass.equals(Union.class)) {
            return UnionView.fromModel((Union) model);
        }
        throw new AssertionError(String.format(
            "Figure %s with type %s was not handled",
            model, klass
        ));
    }
}
