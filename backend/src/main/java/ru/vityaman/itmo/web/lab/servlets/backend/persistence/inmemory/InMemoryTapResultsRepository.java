package ru.vityaman.itmo.web.lab.servlets.backend.persistence.inmemory;

import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;
import ru.vityaman.itmo.web.lab.servlets.backend.model.junk.TapResult;
import ru.vityaman.itmo.web.lab.servlets.backend.persistence.TapResultsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class InMemoryTapResultsRepository
    implements TapResultsRepository {
    private final Map<String, List<TapResult>> resultsByPictureId;

    public InMemoryTapResultsRepository(
        Map<String, List<TapResult>> resultsByPictureId
    ) {
        this.resultsByPictureId = resultsByPictureId;
    }

    @Override
    public void saveResult(TapResult result) {
        resultsByPictureId
            .computeIfAbsent(
                calculateKey(result.pictureId()),
                k -> new ArrayList<>()
            )
            .add(result);
    }

    @Override
    public List<TapResult> getResultsByPictureId(Picture.Id pictureId) {
        return Collections.unmodifiableList(
            resultsByPictureId.getOrDefault(
                calculateKey(pictureId),
                Collections.emptyList()
            )
        );
    }

    @Override
    public void removeTapResultsByPoints(
        Picture.Id pictureId, List<Point> points
    ) {
        resultsByPictureId
            .getOrDefault(calculateKey(pictureId), new ArrayList<>())
            .removeIf(result -> points.contains(result.point()));
    }

    private static String calculateKey(
        Picture.Id pictureId
    ) {
        return String.format(
            "%s:%s:%s",
            pictureId.owner().id(),
            pictureId.name(),
            pictureId.version().hash()
        );
    }
}
