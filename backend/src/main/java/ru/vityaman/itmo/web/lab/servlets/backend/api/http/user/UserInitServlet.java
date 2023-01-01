package ru.vityaman.itmo.web.lab.servlets.backend.api.http.user;

import jakarta.servlet.annotation.WebServlet;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.DeleteRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.GetRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.PostRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.BasicHttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.body.Text;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.BaseServlet;

@WebServlet(
    name = "UserInitServlet",
    value = "api/user/init",
    urlPatterns = {"api/user/init"}
)
public final class UserInitServlet extends BaseServlet {
    @Override
    public HttpResponse get(GetRequest request) {
        final var token =
            request.session().<String>value("username").orElseGet(() -> {
                final var name =
                    backend().userService().generateNewbieToken();
                request.session().set("username", name);
                backend().userService().activateUserAccountWithToken(name);
                return name;
            });
        return BasicHttpResponse.ok(new Text(token));
    }

    @Override
    public HttpResponse post(PostRequest request) {
        return null;
    }

    @Override
    public HttpResponse delete(DeleteRequest request) {
        return null;
    }
}
