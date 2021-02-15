package net.backflip.server.api.player;

import net.backflip.server.api.logger.Logger;
import net.backflip.server.api.message.*;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minestom.server.network.player.PlayerConnection;
import net.nonswag.tnl.api.command.CommandManager;
import net.nonswag.tnl.api.command.executor.Executor;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.logging.Level;

public class Player extends net.minestom.server.entity.Player implements PlayerAudience, Audience, Executor {

    @Nonnull
    private final PlayerExecutorService executor;

    public Player(@Nonnull net.minestom.server.entity.Player player) {
        this(player.getUuid(), player.getUsername(), player.getPlayerConnection());
    }

    public Player(@Nonnull UUID uuid, @Nonnull String username, @Nonnull PlayerConnection playerConnection) {
        super(uuid, username, playerConnection);
        this.executor = new PlayerExecutorService(this);
    }

    @Nonnull
    public PlayerExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void sendMessage(@Nonnull ChatComponent component) {
        sendMessage(component.getText());
    }

    public void sendMessage(@Nonnull MessageKey messageKey, Placeholder... placeholders) {
        Language language = Language.fromLocale(getSettings().getLocale());
        if (language != null) {
            LanguageKey languageKey = new LanguageKey(language, messageKey);
            ChatComponent component = Message.valueOf(languageKey);
            if (component != null) {
                for (Placeholder placeholder : placeholders) {
                    component.setText(component.getText().replace("%" + placeholder.getPlaceholder() + "%", placeholder.getObject().toString()));
                }
                sendMessage(component.getText());
            } else {
                Logger.warn("§cUnknown component§8: §4" + languageKey.toString());
            }
        } else {
            Logger.warn("§cUnknown language§8: §4" + getSettings().getLocale());
        }
    }

    @Override
    public void sendMessage(@Nonnull String s) {
        sendMessage(Component.text(s));
    }

    @Override
    public void sendMessage(@Nonnull String s, @Nonnull Level level) {
        sendMessage(Component.text(s));
    }

    @Nonnull
    @Override
    public String getName() {
        return getUsername();
    }

    public static class PlayerExecutorService extends net.nonswag.tnl.api.command.executor.CommandSender<Player> {

        @Nonnull
        private final Player player;

        protected PlayerExecutorService(@Nonnull Player player) {
            super(player);
            this.player = player;
        }

        @Nonnull
        public Player getPlayer() {
            return player;
        }

        @Nonnull
        @Override
        public String getName() {
            return getPlayer().getName();
        }

        public void runCommand(@Nonnull String command) {
            CommandManager.onCommand(command, this);
        }
    }
}
