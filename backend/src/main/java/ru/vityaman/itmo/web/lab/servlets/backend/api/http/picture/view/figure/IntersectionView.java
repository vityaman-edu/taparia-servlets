package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Figure;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Intersection;

import java.util.List;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IntersectionView extends AggregatorView {
    public IntersectionView(List<FigureView> children) {
        super("intersection", children);
    }

    static IntersectionView fromModel(Intersection model) {
        return new IntersectionView(
            model.children().stream()
                .map(FigureView::fromModelFigure)
                .toList()
        );
    }

    @Override
    public Figure model() {
        return new Intersection(
            children().stream().map(FigureView::model).toList()
        );
    }
}
