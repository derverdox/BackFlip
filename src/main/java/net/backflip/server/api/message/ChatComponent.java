package net.backflip.server.api.message;

import net.kyori.adventure.text.Component;

import javax.annotation.Nonnull;

public class ChatComponent {

    @Nonnull private final LanguageKey languageKey;
    @Nonnull private final Component component;

    public ChatComponent(@Nonnull LanguageKey languageKey, @Nonnull Component component) {
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
