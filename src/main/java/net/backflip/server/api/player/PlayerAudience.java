package net.backflip.server.api.player;

import net.backflip.server.api.message.ChatComponent;
import net.backflip.server.api.message.Placeholder;

import javax.annotation.Nonnull;

public interface PlayerAudience {

    void sendMessage(@Nonnull ChatComponent component);

    void sendMessage(@Nonnull ChatComponent component, @Nonnull Placeholder... placeholders);
}
