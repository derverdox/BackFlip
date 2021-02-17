package net.backflip.server.api.player;

import net.backflip.server.api.logger.Logger;
import net.backflip.server.api.message.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class Messenger implements PlayerAudience {

    @Nonnull private final net.minestom.server.entity.Player player;

    public Messenger(@Nonnull net.minestom.server.entity.Player player) {
        this.player = player;
    }

    @Nonnull
    public net.minestom.server.entity.Player getPlayer() {
        return player;
    }

    public void sendMessage(@Nonnull String message, @Nonnull Placeholder... placeholders) {
        getPlayer().sendMessage(ChatComponent.getText(message, placeholders));
    }

    public void sendMessage(@Nonnull MessageKey messageKey, @Nonnull Placeholder... placeholders) {
        Language language = Language.fromLocale(getPlayer().getSettings().getLocale());
        LanguageKey languageKey = new LanguageKey(language, messageKey);
        ChatComponent component = Message.valueOf(languageKey);
        if (component != null) {
            getPlayer().sendMessage(component.getText(placeholders));
        } else {
            Logger.error("§cUnknown component§8: §4" + languageKey.toString());
        }
    }

    public void kick(@Nonnull String message, @Nonnull Placeholder... placeholders) {
        getPlayer().kick(ChatComponent.getText(message, placeholders));
    }

    public void kick(@Nonnull MessageKey messageKey, @Nonnull Placeholder... placeholders) {
        Language language = Language.fromLocale(getPlayer().getSettings().getLocale());
        LanguageKey languageKey = new LanguageKey(language, messageKey);
        ChatComponent component = Message.valueOf(languageKey);
        if (component != null) {
            getPlayer().kick(component.getText(placeholders));
        } else {
            Logger.error("§cUnknown component§8: §4" + languageKey.toString());
        }
    }

    @Override
    public void sendMessage(@NotNull ChatComponent component) {
        getPlayer().sendMessage(component.getText());
    }

    @Override
    public void sendMessage(@NonNull ChatComponent component, @Nonnull Placeholder... placeholders) {
        getPlayer().sendMessage(component.getText(placeholders));
    }
}
