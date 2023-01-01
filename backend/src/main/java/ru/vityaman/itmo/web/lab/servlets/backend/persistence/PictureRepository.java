package ru.vityaman.itmo.web.lab.servlets.backend.persistence;


import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;

import java.util.Collection;
import java.util.Optional;

public interface PictureRepository {
    Optional<Picture> getFigureById(User user, String name);

    Collection<Picture> getPicturesByOwner(User user);

    void upsertPicture(Picture picture);
}
