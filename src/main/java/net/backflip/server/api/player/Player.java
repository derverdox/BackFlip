package net.backflip.server.api.player;

import net.backflip.server.api.logger.Logger;
import net.backflip.server.api.message.*;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minestom.server.network.player.PlayerConnection;

import javax.annotation.Nonnull;
import java.util.UUID;

public class Player extends net.minestom.server.entity.Player implements PlayerAudience, Audience {

    public Player(@Nonnull net.minestom.server.entity.Player player) {
        this(player.getUuid(), player.getUsername(), player.getPlayerConnection());
    }

    public Player(@Nonnull UUID uuid, @Nonnull String username, @Nonnull PlayerConnection playerConnection) {
        super(uuid, username, playerConnection);
    }

    @Override
    public void sendMessage(@Nonnull ChatComponent component) {
        sendMessage(component.getText());
    }

    public void sendMessage(@Nonnull MessageKey messageKey, Placeholder... placeholders) {
        Language language = Language.fromLocale(getSettings().getLocale());
        LanguageKey languageKey = new LanguageKey(language, messageKey);
        ChatComponent component = Message.valueOf(languageKey);
        if (component != null) {
            sendMessage(component.getText(placeholders));
        } else {
            Logger.warn("§cUnknown component§8: §4" + languageKey.toString());
        }
    }

    @Override
    public void sendMessage(@Nonnull String s) {
        sendMessage(Component.text(s));
    }
}
