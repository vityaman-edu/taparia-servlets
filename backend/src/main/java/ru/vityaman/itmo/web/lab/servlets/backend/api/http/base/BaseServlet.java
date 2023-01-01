package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.Jackson;
import ru.vityaman.itmo.web.lab.servlets.backend.component.Backend;

public abstract class BaseServlet extends HttpServlet {
    private static final Jackson JACKSON = new Jackson(new ObjectMapper());
    private static final Backend BACKEND = buildBackend();

    public final Jackson jackson() {
        return JACKSON;
    }

    public final Backend backend() {
        return BACKEND;
    }

    private static Backend buildBackend() {
        return new Backend();
    }
}
