package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Figure;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Negative;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public final class NegativeView extends FigureView {
    @JsonProperty("child") private final FigureView child;

    public NegativeView(FigureView child) {
        super("negative");
        this.child = child;
    }

    static FigureView fromModel(Negative model) {
        return new NegativeView(
            FigureView.fromModelFigure(model.child())
        );
    }

    @Override
    public Figure model() {
        return new Negative(child.model());
    }
}
