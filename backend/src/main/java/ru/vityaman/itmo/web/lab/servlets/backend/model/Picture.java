package ru.vityaman.itmo.web.lab.servlets.backend.model;

import lombok.Value;
import ru.vityaman.itmo.web.lab.servlets.backend.model.figure.Figure;

@Value
public final class Picture {
    private final Id id;
    private final Figure figure;

    public static Picture create(String ownerId, String name, Figure figure) {
        return new Picture(
            new Id(
                new User(ownerId),
                name,
                new Version(
                    Hashable.hash(String.format(
                        "%s:%s:%s",
                        ownerId, name, figure.hash()
                    ))
                )
            ),
            figure
        );
    }

    public static final class Version {
        private final String hash;

        public Version(String hash) {
            this.hash = hash;
        }

        public String hash() {
            return hash;
        }
    }

    @Value
    public static final class Id {
        private final User owner;
        private final String name;
        private final Version version;
    }
}
