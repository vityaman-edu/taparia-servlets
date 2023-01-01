package ru.vityaman.itmo.web.lab.servlets.backend.component.service.demo;

import ru.vityaman.itmo.web.lab.servlets.backend.component.service.PictureService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.UserService;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Color;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Ellipse;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Intersection;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Negative;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Point;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Polygon;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Union;

import java.util.List;
import java.util.Random;

public final class DemoUserService implements UserService {
    private static final int TOKEN_LENGTH = 20;

    private final PictureService pictureService;

    public DemoUserService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Override
    public String generateNewbieToken() {
        return randomStringWithLength(TOKEN_LENGTH);
    }

    @Override
    public void activateUserAccountWithToken(String token) {
        // CHECKSTYLE:OFF
        final var color = Color.rgb(0xFF, 0x00, 0xAA);
        for (Integer r : List.of(100, 200, 300)) {
            pictureService.savePicture(
                Picture.create(token, "r" + r,
                    new Intersection(List.of(
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
                            Color.white(),
                            new Point(0, 0),
                            new Point(r, -r / 2)
                        ))
                    ))
                )
            );
        }
        // CHECKSTYLE:ON
    }

    private String randomStringWithLength(int targetStringLength) {
        final int leftLimit = 'a';
        final int rightLimit = 'z';
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit
                + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
