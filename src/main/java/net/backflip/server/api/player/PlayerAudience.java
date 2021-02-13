package net.backflip.server.api.player;

import net.backflip.server.api.message.ChatComponent;
import net.kyori.adventure.audience.Audience;
import net.minestom.server.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public interface PlayerAudience {

    void sendMessage(@NonNull ChatComponent component);
}
