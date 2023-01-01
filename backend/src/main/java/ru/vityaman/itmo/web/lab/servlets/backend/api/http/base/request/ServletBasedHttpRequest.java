package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request;

import jakarta.servlet.http.HttpServletRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.session.HttpSession;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.session.ServletBasedHttpSession;

public abstract class ServletBasedHttpRequest implements HttpRequest {
    private final String url;
    private final HttpServletRequest request;

    protected ServletBasedHttpRequest(HttpServletRequest request) {
        url = request.getRequestURL().toString();
        this.request = request;
    }

    @Override
    public final String url() {
        return url;
    }

    @Override
    public final HttpSession session() {
        return new ServletBasedHttpSession(request);
    }
}
