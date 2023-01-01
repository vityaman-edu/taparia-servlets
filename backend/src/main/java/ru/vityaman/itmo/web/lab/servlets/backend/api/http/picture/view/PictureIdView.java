package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Jacksonized
@Value
@Builder
public final class PictureIdView implements View {
    @JsonProperty("owner") private final String owner;
    @JsonProperty("name") private final String name;
    @JsonProperty("version") private final String version;

    public static Optional<PictureIdView> fromParameters(
        Map<String, List<String>> parameters
    ) {
        return
            extract("owner", parameters).flatMap(owner ->
                extract("name", parameters).flatMap(name ->
                    extract("version", parameters).map(version ->
                        new PictureIdView(owner, name, version)
                    )
                )
            );
    }

    private static Optional<String> extract(
        String key, Map<String, List<String>> parameters
    ) {
        return Optional
            .of(parameters.get(key))
            .flatMap(p -> p.stream().findFirst());
    }

    public Picture.Id model() {
        return new Picture.Id(
            new User(owner),
            name,
            new Picture.Version(version)
        );
    }
}
