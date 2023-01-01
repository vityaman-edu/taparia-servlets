package ru.vityaman.itmo.web.lab.servlets.backend.component;

import org.junit.jupiter.api.Test;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.PictureService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.TapService;
import ru.vityaman.itmo.web.lab.servlets.backend.component.service.UserService;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;

import static org.junit.jupiter.api.Assertions.*;

class BackendTest {
    private final Backend backend = new Backend();
    private final UserService userService = backend.userService();
    private final PictureService pictureService = backend.pictureService();
    private final TapService tapService = backend.tapService();

    @Test
    void testUserInitAndRetrievePictures() {
        String token = userService.generateNewbieToken();
        userService.activateUserAccountWithToken(token);
        final var user = new User(token);
        final var pictures =
            pictureService.getPicturesByOwner(user);
        assertEquals(pictures.size(), 3);
        for (var picture : pictures) {
            assertEquals(picture.id().owner(), user);
            assertEquals(picture.id().name().charAt(0), 'r');
        }
    }
}