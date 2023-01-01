package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.session;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public final class ServletBasedHttpSession implements HttpSession {
    private final HttpServletRequest request;

    public ServletBasedHttpSession(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public <T> void set(String key, T value) {
        request.getSession().setAttribute(key, value);
    }

    @Override
    public <T> Optional<T> value(String key) {
        return Optional.ofNullable((T) request.getSession().getAttribute(key));
    }
}
