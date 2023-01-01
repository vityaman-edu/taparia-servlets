package ru.vityaman.itmo.web.lab.servlets.backend.http.picture.view.figure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.HttpRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.Jackson;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.EllipseView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.FigureView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.IntersectionView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.NegativeView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.PolygonView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.UnionView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.VectorView;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Color;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Ellipse;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Intersection;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Negative;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Polygon;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Union;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FigureViewTest {
    private final Jackson jackson = new Jackson(new ObjectMapper());

    @Test
    void testEllipseFromModelConversions() {
        final var model = new Ellipse(
            Color.parse("#000000"),
            new Point(0, 10),
            new Ellipse.Radius(20, 30)
        );
        final var view = EllipseView.fromModel(model);
        assertEquals(view.color(), "#000000");
        assertEquals(view.center().x(), 0);
        assertEquals(view.center().y(), 10);
        assertEquals(view.radius().x(), 20);
        assertEquals(view.radius().y(), 30);
    }

    @Test
    void testFigureCompositionFromModelConversions() {
        final var color = Color.parse("#000000");
        final var r = 10;

        final var givenModel = new Intersection(List.of(
            new Union(List.of(
                Polygon.rectangle(
                    color,
                    new Point(-r, r / 2),
                    new Point(0, 0)
                ),
                new Polygon(color, List.of(
                    new Point(0, 0),
                    new Point(0, r),
                    new Point(r, 0)
                )),
                Ellipse.circle(
                    color,
                    new Point(0, 0),
                    r / 2
                )
            )),
            new Negative(Polygon.rectangle(
                color,
                new Point(0, 0),
                new Point(r, -r / 2)
            ))
        ));

        final var givenView =
            FigureView.fromModelFigure(givenModel);

        final var expectedView =
            new IntersectionView(List.of(
                new UnionView(List.of(
                    PolygonView.fromModel(Polygon.rectangle(
                        color,
                        new Point(-r, r / 2),
                        new Point(0, 0)
                    )),
                    new PolygonView(color.hex(), List.of(
                        new VectorView(0, 0),
                        new VectorView(0, r),
                        new VectorView(r, 0)
                    )),
                    EllipseView.fromModel(Ellipse.circle(
                        color,
                        new Point(0, 0),
                        r / 2
                    ))
                )),
                new NegativeView(PolygonView.fromModel(
                    Polygon.rectangle(
                        color,
                        new Point(0, 0),
                        new Point(r, -r / 2)
                    )
                ))
            ));

        assertEquals(givenView, expectedView);
        assertRoundtripSucceed(givenView, givenView.getClass());
        assertRoundtripSucceed(expectedView, expectedView.getClass());
    }

    private void assertRoundtripSucceed(
        View view, Class<? extends View> klass
    ) {
        try {
            StringWriter out = new StringWriter();
            PrintWriter writer = new PrintWriter(out);
            jackson.entity(view).writeTo(writer);
            writer.flush();
            String serializedView = out.toString();

            assertEquals(
                view,
                jackson.parseBody(
                    new HttpRequest.WithBody.Body() {
                        @Override
                        public String contentType() {
                            return "application/json";
                        }

                        @Override
                        public InputStream stream() {
                            return new ByteArrayInputStream(
                                serializedView.getBytes()
                            );
                        }
                    },
                    klass
                )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}