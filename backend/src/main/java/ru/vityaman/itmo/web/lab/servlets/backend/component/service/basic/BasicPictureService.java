package ru.vityaman.itmo.web.lab.servlets.backend.component.service.basic;


import ru.vityaman.itmo.web.lab.servlets.backend.component.service.PictureService;
import ru.vityaman.itmo.web.lab.servlets.backend.model.Picture;
import ru.vityaman.itmo.web.lab.servlets.backend.model.User;
import ru.vityaman.itmo.web.lab.servlets.backend.persistence.PictureRepository;

import java.util.Collection;
import java.util.Optional;

public final class BasicPictureService implements PictureService {
    private final PictureRepository repository;

    public BasicPictureService(PictureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Picture> getLatestPicture(User owner, String name) {
        return repository.getFigureById(owner, name);
    }

    @Override // TODO: use versions
    public Optional<Picture> getPictureById(Picture.Id id) {
        return repository.getFigureById(id.owner(), id.name());
    }

    @Override
    public Collection<Picture> getPicturesByOwner(User user) {
        return repository.getPicturesByOwner(user);
    }

    @Override
    public void savePicture(Picture picture) {
        repository.upsertPicture(picture);
    }
}
