package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.HttpRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.response.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class Jackson {
    private final ObjectMapper objectMapper;

    public Jackson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T parseBody(HttpRequest.WithBody.Body body, Class<T> klass)
        throws DeserializationException {
        if (!body.contentType().equals("application/json")) {
            throw new DeserializationException(
                "Deserialization exception: "
                    + "content type must be application/json"
            );
        }
        try {
            return objectMapper.readValue(body.stream(), klass);
        } catch (IOException e) {
            throw new DeserializationException(e.getMessage());
        }
    }

    public HttpResponse.Body entity(View view) {
        return new ResponseEntity(view);
    }

    public HttpResponse.Body entity(List<? extends View> views) {
        return new ResponseEntity(views);
    }

    @ToString
    private final class ResponseEntity implements HttpResponse.Body {
        private final Object object;

        private ResponseEntity(View view) {
            object = view;
        }

        private ResponseEntity(List<? extends View> views) {
            object = views;
        }

        @Override
        public String contentType() {
            return "application/json";
        }

        @Override
        public void writeTo(PrintWriter writer) throws IOException {
            String json = objectMapper.writeValueAsString(object);
            writer.println(json);
        }
    }

    public static class DeserializationException extends Exception {
        DeserializationException(String message) {
            super(message);
        }
    }
}
