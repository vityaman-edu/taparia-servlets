package ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Value
@NonFinal
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class AggregatorView extends FigureView {
    @JsonProperty("children") private final List<FigureView> children;

    protected AggregatorView(String type, List<FigureView> children) {
        super(type);
        this.children = children;
    }
}
