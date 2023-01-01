package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture;

import jakarta.servlet.annotation.WebServlet;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.DeleteRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.GetRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.PostRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.BasicHttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.ResponseStatus;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.Jackson;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.DeleteTapsRequestView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.PictureIdView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.TapResultView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.TapView;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.TapService;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.BaseServlet;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Color;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Ellipse;
import ru.vityaman.itmo.web.lab.servlets.backend.model.junk.TapResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(
    name = "PictureTapsServlet",
    value = "/api/picture/taps",
    urlPatterns = {"/api/picture/taps"}
)
public final class PictureTapsServlet extends BaseServlet {

    @Override
    public HttpResponse get(GetRequest request) {
        final Optional<Picture.Id> mbPictureId = PictureIdView
            .fromParameters(request.parameters())
            .map(PictureIdView::model);
        if (mbPictureId.isEmpty()) {
            return BasicHttpResponse.fail(
                ResponseStatus.BAD_REQUEST,
                "owner, name and version must be provided"
            );
        }
        try {
            List<TapResult> results = backend()
                .tapService()
                .getTapResults(mbPictureId.get());
            return BasicHttpResponse.ok(
                jackson().entity(
                    results.stream()
                        .map(TapResultView::fromModel)
                        .collect(Collectors.toList())
                )
            );
        } catch (TapService.PictureNotFoundException e) {
            return BasicHttpResponse.fail(
                ResponseStatus.NOT_FOUND, e.getMessage()
            );
        }
    }

    @Override
    public HttpResponse post(PostRequest request) {
        try {
            final var tap =
                jackson().parseBody(request.body(), TapView.class);
            final var result = backend().tapService()
                .tap(tap.picture().model(), tap.point().model());
            return BasicHttpResponse.ok(jackson()
                .entity(TapResultView.fromModel(result))
            );
        } catch (Jackson.DeserializationException e) {
            return BasicHttpResponse.fail(
                ResponseStatus.BAD_REQUEST,
                "body must contains a tap: " + e.getMessage()
            );
        } catch (TapService.PictureNotFoundException e) {
            return BasicHttpResponse.fail(
                ResponseStatus.NOT_FOUND, e.getMessage()
            );
        }
    }

    @Override
    public HttpResponse delete(DeleteRequest request) {
        try {
            final var req = jackson().parseBody(
                request.body(), DeleteTapsRequestView.class
            );
            //CHECKSTYLE:OFF
            final var deletedResults =
                backend()
                    .tapService()
                    .removeTapResultsInEllipse(
                        req.pictureId().model(),
                        Ellipse.circle(
                            Color.black(),
                            req.point().model(),
                            req.radius()
                        )
                    );
            //CHECKSTYLE:ON
            return BasicHttpResponse.ok(jackson()
                .entity(deletedResults.stream()
                    .map(TapResultView::fromModel)
                    .toList()
                )
            );
        } catch (Jackson.DeserializationException e) {
            return BasicHttpResponse.fail(
                ResponseStatus.BAD_REQUEST,
                "request is invalid: " + e.getMessage()
            );
        } catch (TapService.PictureNotFoundException e) {
            return BasicHttpResponse.fail(
                ResponseStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }
}
