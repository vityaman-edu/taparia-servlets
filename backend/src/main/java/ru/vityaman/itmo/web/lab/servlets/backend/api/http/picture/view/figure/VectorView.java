package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;

@Jacksonized
@Value
@Builder
@RequiredArgsConstructor
public final class VectorView implements View {
    @JsonProperty("x") private final float x;
    @JsonProperty("y") private final float y;

    public static VectorView fromModel(Point model) {
        return new VectorView(model.x(), model.y());
    }

    public Point model() {
        return new Point((long) x, (long) y);
    }
}
