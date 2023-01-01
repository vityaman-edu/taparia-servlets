package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Figure;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Union;

import java.util.List;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public final class UnionView extends AggregatorView {
    public UnionView(List<FigureView> children) {
        super("union", children);
    }

    static FigureView fromModel(Union model) {
        return new UnionView(
            model.children().stream()
                .map(FigureView::fromModelFigure)
                .toList()
        );
    }

    @Override
    public Figure model() {
        return new Union(
            children().stream().map(FigureView::model).toList()
        );
    }
}
