package ru.vityaman.itmo.web.lab.servlets.backend.component.service.basic;

import ru.vityaman.itmo.web.lab.servlets.backend.component.service.PictureService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.TapService;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Ellipse;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;
import ru.vityaman.itmo.web.lab.servlets.backend.model.junk.TapResult;
import ru.vityaman.itmo.web.lab.servlets.backend.persistence.TapResultsRepository;

import java.util.List;

public final class BasicTapService implements TapService {
    private final PictureService pictureService;
    private final TapResultsRepository repository;

    public BasicTapService(
        PictureService pictureService,
        TapResultsRepository repository
    ) {
        this.pictureService = pictureService;
        this.repository = repository;
    }

    @Override
    public TapResult tap(Picture.Id pictureId, Point point)
        throws PictureNotFoundException {
        Picture picture = pictureService.getPictureById(pictureId)
            .orElseThrow(() -> new PictureNotFoundException(pictureId));
        TapResult result = new TapResult(
            picture.id(),
            point,
            picture.figure().contains(point)
        );
        repository.saveResult(result);
        return result;
    }

    @Override
    public List<TapResult> getTapResults(Picture.Id pictureId) {
        return repository.getResultsByPictureId(pictureId);
    }

    @Override
    public List<TapResult> removeTapResultsInEllipse(
        Picture.Id pictureId, Ellipse ellipse
    ) {
        List<TapResult> resultsToRemove = getTapResults(pictureId).stream()
            .filter(result -> ellipse.contains(result.point()))
            .toList();
        List<Point> points =
            resultsToRemove.stream().map(TapResult::point).toList();
        repository.removeTapResultsByPoints(pictureId, points);
        return resultsToRemove;
    }
}
