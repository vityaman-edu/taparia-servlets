package ru.vityaman.itmo.web.lab.servlets.backend.persistence;

import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;
import ru.vityaman.itmo.web.lab.servlets.backend.model.junk.TapResult;

import java.util.List;

public interface TapResultsRepository {
    void saveResult(TapResult result);

    List<TapResult> getResultsByPictureId(Picture.Id pictureId);

    void removeTapResultsByPoints(
        Picture.Id pictureId, List<Point> points
    );
}
