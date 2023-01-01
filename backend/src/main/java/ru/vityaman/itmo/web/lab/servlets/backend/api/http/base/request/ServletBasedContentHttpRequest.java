package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;

public final class ServletBasedContentHttpRequest
    extends ServletBasedHttpRequest
    implements PostRequest, DeleteRequest {

    private final HttpServletRequest request;

    public ServletBasedContentHttpRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public Body body() {
        return new Body() {
            @Override
            public String contentType() {
                return request.getContentType();
            }

            @Override
            public InputStream stream() throws IOException {
                return request.getInputStream();
            }
        };
    }
}
