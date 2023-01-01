package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.session;

import java.util.Optional;

public interface HttpSession {
    <T> void set(String key, T value);

    <T> Optional<T> value(String key);
}
