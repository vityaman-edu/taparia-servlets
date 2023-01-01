package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response;

public enum ResponseStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request");

    private final int code;
    private final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
