package ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ServletBasedGetRequest
    extends ServletBasedHttpRequest
    implements GetRequest {
    private final Map<String, List<String>> parameters;

    public ServletBasedGetRequest(HttpServletRequest request) {
        super(request);
        parameters = request.getParameterMap()
            .entrySet()
            .stream()
            .map(entry -> new Map.Entry<String, List<String>>() {
                @Override
                public String getKey() {
                    return entry.getKey();
                }

                @Override
                public List<String> getValue() {
                    return Arrays.asList(entry.getValue());
                }

                @Override
                public List<String> setValue(List<String> value) {
                    throw new UnsupportedOperationException();
                }
            })
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Override
    public Map<String, List<String>> parameters() {
        return parameters;
    }
}
