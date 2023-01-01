package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.FigureView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;

@Jacksonized
@Value
@SuperBuilder
@RequiredArgsConstructor
public final class PictureView implements View {
    @JsonProperty("owner") private final String owner;
    @JsonProperty("name") private final String name;
    @JsonProperty("version") private final String version;
    @JsonProperty("figure") private final FigureView figure;

    public static PictureView fromModel(Picture model) {
        return new PictureView(
            model.id().owner().id(),
            model.id().name(),
            model.id().version().hash(),
            FigureView.fromModelFigure(model.figure())
        );
    }

    public Picture model() {
        if (version == null) {
            return Picture.create(owner, name, figure.model());
        }
        return new Picture(
            new Picture.Id(
                new User(owner),
                name,
                new Picture.Version(version)
            ),
            figure.model()
        );
    }
}
