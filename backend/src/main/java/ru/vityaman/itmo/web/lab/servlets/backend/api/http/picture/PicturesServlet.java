package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture;

import jakarta.servlet.annotation.WebServlet;
import lombok.extern.log4j.Log4j2;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.DeleteRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.GetRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.PostRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.BasicHttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;
import ru.vityaman.itmo.web.lab.servlары, можно и ets.backend.api.http.base.response.ResponseStatus;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.Jackson;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.PictureView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.BaseServlet;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;

@WebServlet(
    name = "PicturesServlet",
    value = "/api/pictures",
    urlPatterns = {"/api/pictures"}
)
@Log4j2
public final class PicturesServlet extends BaseServlet {
    @Override
    public void initialize() {

    }

    @Override
    public HttpResponse get(GetRequest request) {
        if (!request.parameters().containsKey("owner_id")) {
            return BasicHttpResponse.fail(
                ResponseStatus.BAD_REQUEST, "owner_id was not provided"
            );
        }
        final var ownerId = request.parameters().get("owner_id").get(0);
        if (request.parameters().containsKey("name")) {
            final var pictureName =
                request.parameters().get("name").get(0);
            return getLatestPicture(ownerId, pictureName);
        } else {
            return getPicturesByOwner(ownerId);
        }
    }

    private HttpResponse getLatestPicture(String ownerId, String pictureName) {
        final var mbPicture = backend().pictureService()
            .getLatestPicture(new User(ownerId), pictureName);
        if (mbPicture.isEmpty()) {
            return BasicHttpResponse.fail(
                ResponseStatus.NOT_FOUND, "picture not found"
            );
        }
        final var result = jackson()
            .entity(PictureView.fromModel(mbPicture.get()));
        log.info(result.toString());
        return BasicHttpResponse.ok(result);
    }

    private HttpResponse getPicturesByOwner(String ownerId) {
        final var pictures =
            backend().pictureService().getPicturesByOwner(new User(ownerId));
        final var views = jackson()
            .entity(pictures.stream().map(PictureView::fromModel).toList());
        return BasicHttpResponse.ok(views);
    }

    @Override
    public HttpResponse post(PostRequest request) {
        try {
            final var pictureView =
                jackson().parseBody(request.body(), PictureView.class);
            final var picture = pictureView.model();
            backend().pictureService().savePicture(picture);
            return BasicHttpResponse.unit();
        } catch (Jackson.DeserializationException e) {
            return BasicHttpResponse.fail(
                ResponseStatus.BAD_REQUEST,
                String.format("invalid picture body: %s", e.getMessage())
            );
        }
    }

    @Override
    public HttpResponse delete(DeleteRequest request) {
        return null;
    }
}
