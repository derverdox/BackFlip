package net.backflip.server.api.player;

import net.backflip.server.api.message.ChatComponent;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface PlayerAudience {

    void sendMessage(@NonNull ChatComponent component);
}
