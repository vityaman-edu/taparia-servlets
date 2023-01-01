package ru.vityaman.itmo.web.lab.servlets.backend.component.service;

import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;

import java.util.Collection;
import java.util.Optional;

public interface PictureService {
    Optional<Picture> getLatestPicture(User owner, String name);

    Optional<Picture> getPictureById(Picture.Id id);

    Collection<Picture> getPicturesByOwner(User user);

    void savePicture(Picture picture);
}
