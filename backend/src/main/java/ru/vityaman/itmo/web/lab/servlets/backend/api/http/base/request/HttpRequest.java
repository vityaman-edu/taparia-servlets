package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request;

import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.session.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface HttpRequest {
    String url();

    HttpSession session();

    interface WithBody extends HttpRequest {
        Body body();

        interface Body {
            String contentType();

            InputStream stream() throws IOException;
        }
    }

    interface WithParameters extends HttpRequest {
        Map<String, List<String>> parameters();
    }
}
