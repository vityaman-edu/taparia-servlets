package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.DeleteRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.GetRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.PostRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.ServletBasedContentHttpRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.ServletBasedGetRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.ResponseStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class HttpServlet extends jakarta.servlet.http.HttpServlet {
    private final ConcurrentMap<Class<? extends Exception>, ResponseStatus>
        responseStatusByExceptionType = new ConcurrentHashMap<>();

    public void initialize() {

    }

    public final void setResponseStatusOfException(
        Class<? extends Exception> exception, ResponseStatus status
    ) {
        responseStatusByExceptionType.put(exception, status);
    }

    public abstract HttpResponse get(GetRequest request);

    public abstract HttpResponse post(PostRequest request);

    public abstract HttpResponse delete(DeleteRequest request);

    @Override
    public final void init() {
        initialize();
    }

    @Override
    protected final void service(
        HttpServletRequest req,
        HttpServletResponse resp
    ) throws IOException, ServletException {
        try {
            super.service(req, resp);
        } catch (Exception e) {
            final Class<? extends Exception> type = e.getClass();
            if (!responseStatusByExceptionType.containsKey(type)) {
                throw new ServletException(e);
            }
            final ResponseStatus status =
                responseStatusByExceptionType.get(type);
            resp.setStatus(status.code());
            try (ServletOutputStream out = resp.getOutputStream()) {
                out.println("{");
                out.println("  \"code\": " + status.code() + ",");
                out.println(
                    "  \"description\": \"" + status.message() + "\","
                );
                out.println("  \"message\": \"" + e.getMessage() + "\"");
                out.println("}");
            }
        }
    }

    @Override
    protected final void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        transferState(
            get(new ServletBasedGetRequest(request)),
            response
        );
    }

    @Override
    protected final void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        transferState(
            post(new ServletBasedContentHttpRequest(request)),
            response
        );
    }

    @Override
    protected final void doDelete(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        transferState(
            delete(new ServletBasedContentHttpRequest(request)),
            response
        );
    }

    @Override
    protected final void doOptions(
        HttpServletRequest req,
        HttpServletResponse resp
    ) {
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Headers", "*");
        resp.addHeader(
            "Access-Control-Allow-Methods",
            "GET, OPTIONS, POST, DELETE"
        );
    }

    private void transferState(HttpResponse elegant, HttpServletResponse ugly)
        throws IOException {
        ugly.setStatus(elegant.status().code());
        ugly.setContentType(elegant.body().contentType());
        ugly.setHeader("Access-Control-Allow-Credentials", "true");
        try (PrintWriter body = ugly.getWriter()) {
            elegant.body().writeTo(body);
        }
    }
}
