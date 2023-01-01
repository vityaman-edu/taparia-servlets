package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response;

import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.body.Empty;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.body.Text;

public final class BasicHttpResponse implements HttpResponse {
    private final ResponseStatus status;
    private final Body body;

    public BasicHttpResponse(ResponseStatus status, Body body) {
        this.status = status;
        this.body = body;
    }

    public static HttpResponse ok(Body body) {
        return new BasicHttpResponse(ResponseStatus.OK, body);
    }

    public static HttpResponse fail(ResponseStatus status, String message) {
        return new BasicHttpResponse(status, new Text(message));
    }

    public static HttpResponse unit() {
        return new BasicHttpResponse(ResponseStatus.OK, Empty.body());
    }

    @Override
    public ResponseStatus status() {
        return status;
    }

    @Override
    public Body body() {
        return body;
    }
}
