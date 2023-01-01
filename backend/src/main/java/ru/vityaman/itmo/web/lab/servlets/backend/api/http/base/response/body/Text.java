package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.body;

import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;

import java.io.PrintWriter;

public final class Text implements HttpResponse.Body {
    private final String value;

    public Text(String value) {
        this.value = value;
    }

    @Override
    public String contentType() {
        return "text/plain";
    }

    @Override
    public void writeTo(PrintWriter writer) {
        writer.write(value);
    }
}
