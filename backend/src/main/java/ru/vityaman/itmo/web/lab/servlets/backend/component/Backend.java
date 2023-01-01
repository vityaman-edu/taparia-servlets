package ru.vityaman.itmo.web.lab.servlets.backend.component;

import lombok.Getter;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.PictureService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.TapService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.UserService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.basic.BasicPictureService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.basic.BasicTapService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.demo.DemoUserService;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Color;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Polygon;
import ru.vityaman.itmo.web.lab.servlets.backend.persistence.inmemory.InMemoryPictureRepository;
import ru.vityaman.itmo.web.lab.servlets.backend.persistence.inmemory.InMemoryTapResultsRepository;

import java.util.HashMap;
import java.util.Map;

public final class Backend {
    @Getter private final TapService tapService;
    @Getter private final PictureService pictureService;
    @Getter private final UserService userService;

    public Backend() {
        final var side = 200;

        pictureService =
            new BasicPictureService(
                new InMemoryPictureRepository(
                    new HashMap<>(Map.of(
                        User.withId("tester"),
                        new HashMap<>(Map.of(
                            "sketch",
                            Picture.create(
                                "tester",
                                "sketch",
                                Polygon.rectangle(
                                    Color.black(),
                                    Point.of(-side / 2, side / 2),
                                    Point.of(side / 2, -side / 2)
                                )
                            ),
                            "test",
                            Picture.create(
                                "tester",
                                "test",
                                Polygon.rectangle(
                                    Color.black(),
                                    Point.of(-side, side),
                                    Point.of(side, -side)
                                )
                            )
                        ))
                    ))
                )
            );

        tapService =
            new BasicTapService(
                pictureService,
                new InMemoryTapResultsRepository(
                    new HashMap<>()
                )
            );

        userService = new DemoUserService(pictureService);
    }
}
