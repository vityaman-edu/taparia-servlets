package ru.vityaman.itmo.web.lab.servlets.backend.http.base.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.request.HttpRequest;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.Jackson;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.base.view.View;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.PictureIdView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.PictureView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.TapView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.EllipseView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.PolygonView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.UnionView;
import ru.vityaman.itmo.web.lab.servlets.backend.api.http.picture.view.figure.VectorView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JacksonTest {
    private final Jackson jackson = new Jackson(new ObjectMapper());

    @Test
    void testTapViewRoundtrip() {
        assertRoundtripSucceed(
            TapView.builder()
                .picture(PictureIdView.builder()
                    .name("picture-name")
                    .owner("picture-owner")
                    .version("picture-version")
                    .build())
                .point(VectorView.builder()
                    .x(10)
                    .y(20)
                    .build())
                .build(),
            TapView.class
        );
    }

    @Test
    void testPictureViewRoundtrip() {
        assertRoundtripSucceed(
            new PictureView(
                "picture-owner",
                "picture-name",
                "picture-version",
                new UnionView(List.of(
                    new EllipseView(
                        "#000000",
                        new VectorView(10, 4),
                        new VectorView(1, 9)
                    ),
                    new PolygonView(
                        "#000000",
                        List.of(
                            new VectorView(0, 0),
                            new VectorView(10, 100),
                            new VectorView(60, 0)
                        )
                    )
                ))
            ),
            PictureView.class
        );
    }

    @Test
    void testFigureViewRoundtrip() {
        assertRoundtripSucceed(
            new EllipseView(
                "#000000",
                new VectorView(10, 20),
                new VectorView(5, 5)
            ),
            EllipseView.class
        );
        assertRoundtripSucceed(
            new PolygonView(
                "#000000",
                List.of(
                    new VectorView(0, 0),
                    new VectorView(10, 0),
                    new VectorView(10, 10),
                    new VectorView(0, 10)
                )
            ),
            PolygonView.class
        );
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