package net.backflip.server.api.player;

import net.backflip.server.api.logger.Color;
import net.backflip.server.api.logger.Logger;
import net.nonswag.tnl.api.command.executor.Executor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class Console implements Executor {

    @Nonnull private static final Console instance = new Console();

    @Override
    public void sendMessage(@NotNull String s) {
        Logger.info(s);
    }

    @Override
    public void sendMessage(@NotNull String s, @NotNull Level level) {
        if (level.equals(Level.SEVERE)) {
            Logger.error(s);
        } else if (level.equals(Level.WARNING)) {
            Logger.warn(s);
        } else if (level.equals(Level.CONFIG)) {
            Logger.debug(s);
        } else {
            Logger.info(s);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Console";
    }

    @Nonnull
    public static Console getInstance() {
        return instance;
    }
}
