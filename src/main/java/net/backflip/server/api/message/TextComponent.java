package net.backflip.server.api.message;

import net.kyori.adventure.text.Component;

import javax.annotation.Nonnull;

public class TextComponent {

    @Nonnull private final LanguageKey languageKey;
    @Nonnull private final Component component;

    public TextComponent(@Nonnull LanguageKey languageKey, @Nonnull Component component) {
        this.languageKey = languageKey;
        this.component = component;
    }

    @Nonnull
    public LanguageKey getLanguageKey() {
        return languageKey;
    }

    @Nonnull
    public Component getComponent() {
        return component;
    }
}
