package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.VectorView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;

@Jacksonized
@Value
@Builder
public final class TapView implements View {
    @JsonProperty("picture") private final PictureIdView picture;
    @JsonProperty("point") private final VectorView point;
}
