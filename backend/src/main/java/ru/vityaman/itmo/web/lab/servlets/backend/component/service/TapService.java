package ru.vityaman.itmo.web.lab.servlets.backend.component.service;

import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Ellipse;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;
import ru.vityaman.itmo.web.lab.servlets.backend.model.junk.TapResult;

import java.util.List;

public interface TapService {
    TapResult tap(Picture.Id pictureId, Point point)
        throws PictureNotFoundException;

    List<TapResult> getTapResults(Picture.Id pictureId)
        throws PictureNotFoundException;

    List<TapResult> removeTapResultsInEllipse(
        Picture.Id pictureId, Ellipse ellipse
    ) throws PictureNotFoundException;

    class PictureNotFoundException extends Exception {
        public PictureNotFoundException(Picture.Id pictureId) {
            super(String.format(
                "Picture with id %s not found", pictureId
            ));
        }
    }
}
