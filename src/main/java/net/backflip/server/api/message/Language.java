package net.backflip.server.api.message;

import javax.annotation.Nonnull;
import java.io.File;

public enum Language {
    ROOT("system", ""),
    ENGLISH("english", "en_us"),
    GERMAN("german", "de_eu"),
    ;

    @Nonnull
    private final String name;
    @Nonnull
    private final String shorthand;
    @Nonnull
    private final File file;

    Language(@Nonnull String name, @Nonnull String shorthand) {
        this.name = name;
        this.shorthand = shorthand;
        this.file = new File("messages" + (getShorthand().isEmpty() ? "" : "-" + getShorthand()) + ".json");
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getShorthand() {
        return shorthand;
    }

    @Nonnull
    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", shorthand='" + shorthand + '\'' +
                ", file=" + file +
                '}';
    }
}
