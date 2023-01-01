package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.body;

import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;

import java.io.PrintWriter;

public final class Empty implements HttpResponse.Body {
    @Override
    public String contentType() {
        return "application/text";
    }

    @Override
    public void writeTo(PrintWriter writer) {
        // Do nothing
    }

    private static final Empty INSTANCE = new Empty();

    public static HttpResponse.Body body() {
        return INSTANCE;
    }
}
