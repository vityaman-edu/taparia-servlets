package ru.vityaman.itmo.web.lab.servlets.backend.model.junk;

import lombok.Value;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;

@Value
public final class TapResult {
    private final Picture.Id pictureId;
    private final Point point;
    private final boolean isHit;
}
