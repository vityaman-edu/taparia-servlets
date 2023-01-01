package ru.vityaman.itmo.web.lab.servlets.backend.api.http.ping;

import jakarta.servlet.annotation.WebServlet;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.DeleteRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.GetRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.PostRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.BasicHttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.body.Text;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.BaseServlet;

@WebServlet(
    name = "PingPongServlet",
    value = "/api/ping",
    urlPatterns = {"/api/ping"}
)
public final class PingPongServlet extends BaseServlet {
    @Override
    public HttpResponse get(GetRequest request) {
        return BasicHttpResponse.ok(new Text("pong"));
    }

    @Override
    public HttpResponse post(PostRequest request) {
        return null; // TODO
    }

    @Override
    public HttpResponse delete(DeleteRequest request) {
        return null;
    }
}
