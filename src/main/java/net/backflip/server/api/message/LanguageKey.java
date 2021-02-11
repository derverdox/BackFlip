package net.backflip.server.api.message;

import javax.annotation.Nonnull;

public class LanguageKey {

    @Nonnull
    private final Language language;
    @Nonnull
    private final String key;

    public LanguageKey(@Nonnull Language language, @Nonnull String key) {
        this.language = language;
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return key;
    }

    @Nonnull
    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "LanguageKey{" +
                "language=" + language +
                ", key='" + key + '\'' +
                '}';
    }
}
