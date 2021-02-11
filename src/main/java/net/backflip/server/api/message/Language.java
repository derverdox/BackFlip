package net.backflip.server.api.message;

import javax.annotation.Nonnull;

public enum Language {
    ENGLISH("english", "en_us"),
    GERMAN("german", "de_eu"),
    ;

    @Nonnull
    private final String name;
    @Nonnull
    private final String shorthand;

    Language(@Nonnull String name, @Nonnull String shorthand) {
        this.name = name;
        this.shorthand = shorthand;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getShorthand() {
        return shorthand;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", shorthand='" + shorthand + '\'' +
                '}';
    }
}
