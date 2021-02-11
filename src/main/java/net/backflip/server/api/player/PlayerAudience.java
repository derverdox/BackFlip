package net.backflip.server.api.player;

import net.backflip.server.api.message.ChatComponent;
import net.kyori.adventure.audience.Audience;
import net.minestom.server.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PlayerAudience implements Audience {

    @Nonnull private final Player player;

    public PlayerAudience(@Nonnull Player player) {
        this.player = player;
    }

    @Nonnull
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "PlayerAudience{" +
                "player=" + player +
                '}';
    }

    public void sendMessage(@NonNull ChatComponent component) {
        sendMessage(component.getComponent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerAudience that = (PlayerAudience) o;
        return player.equals(that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }
}
