package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.VectorView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;
import ru.vityaman.itmo.web.lab.servlets.backend.model.junk.TapResult;

@Jacksonized
@Value
@Builder
public final class TapResultView implements View {
    @JsonProperty("picture_version") private final String pictureVersion;
    @JsonProperty("point") private final VectorView point;
    @JsonProperty("is_hit") private final boolean isHit;

    public static TapResultView fromModel(TapResult model) {
        return new TapResultView(
            model.pictureId().version().hash(),
            VectorView.fromModel(model.point()),
            model.isHit()
        );
    }
}
