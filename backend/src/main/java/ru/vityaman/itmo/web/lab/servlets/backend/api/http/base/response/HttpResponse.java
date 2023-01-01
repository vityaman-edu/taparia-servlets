package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response;

import java.io.IOException;
import java.io.PrintWriter;

public interface HttpResponse {
    // TODO: add method 'fillServletResponseEntity'

    ResponseStatus status();

    Body body();

    interface Body {
        String contentType();

        void writeTo(PrintWriter writer) throws IOException;
    }
}
