package net.backflip.server.api.message;

import javax.annotation.Nonnull;
import java.util.Objects;

public class LanguageKey {

    @Nonnull
    private final Language language;
    @Nonnull
    private final MessageKey messageKey;

    public LanguageKey(@Nonnull Language language, @Nonnull MessageKey messageKey) {
        this.language = language;
        this.messageKey = messageKey;
    }

    @Nonnull
    public MessageKey getMessageKey() {
        return messageKey;
    }

    @Nonnull
    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "LanguageKey{" +
                "language=" + language +
                ", messageKey=" + messageKey +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageKey that = (LanguageKey) o;
        return language == that.language && messageKey.equals(that.messageKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, messageKey);
    }
}
