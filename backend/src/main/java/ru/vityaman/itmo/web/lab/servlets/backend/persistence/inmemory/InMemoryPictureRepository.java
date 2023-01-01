package ru.vityaman.itmo.web.lab.servlets.backend.persistence.inmemory;


import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;
import ru.vityaman.itmo.web.lab.servlets.backend.persistence.PictureRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class InMemoryPictureRepository implements PictureRepository {
    private final Map<User, Map<String, Picture>> pictures;

    public InMemoryPictureRepository(
        Map<User, Map<String, Picture>> pictures
    ) {
        this.pictures = pictures;
    }

    @Override
    public Optional<Picture> getFigureById(User user, String name) {
        return Optional
            .ofNullable(pictures.get(user))
            .flatMap(p -> Optional.ofNullable(p.get(name)));
    }

    @Override
    public Collection<Picture> getPicturesByOwner(User user) {
        return Optional.ofNullable(pictures.get(user))
            .map(Map::values)
            .map(Collections::unmodifiableCollection)
            .orElseGet(Collections::emptyList);
    }

    @Override
    public void upsertPicture(Picture picture) {
        pictures
            .computeIfAbsent(picture.id().owner(), k -> new HashMap<>())
            .put(picture.id().name(), picture);
    }
}
