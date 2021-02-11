package net.backflip.server.api.settings;

import net.backflip.server.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Settings {

    @Nonnull private static final Settings instance = new Settings();

    @Nonnull private final File file = new File("settings.json");
    @Nonnull private final Setting<InetSocketAddress> address = new Setting<>(null);
    @Nonnull private final Setting<String> velocitySecret = new Setting<>(null);
    @Nonnull private final Setting<Boolean> autoUpdater = new Setting<>(null);
    @Nonnull private final Setting<Boolean> tabCompleter = new Setting<>(null);
    @Nonnull private final Setting<Boolean> chatCompleter = new Setting<>(null);
    @Nonnull private final Setting<Boolean> forgeSupport = new Setting<>(null);
    @Nonnull private final Setting<Boolean> optifineSupport = new Setting<>(null);
    @Nonnull private final Setting<Boolean> velocitySupport = new Setting<>(null);

    protected Settings() {
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Logger.info("Generated '" + getFile().getName() + "' file at '" + getFile().getAbsolutePath() + "'");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nonnull
    public File getFile() {
        return file;
    }

    @Nonnull
    public Setting<InetSocketAddress> getAddress() {
        return address;
    }

    @Nonnull
    public Setting<String> getVelocitySecret() {
        return velocitySecret;
    }

    @Nonnull
    public Setting<Boolean> getAutoUpdater() {
        return autoUpdater;
    }

    @Nonnull
    public Setting<Boolean> getTabCompleter() {
        return tabCompleter;
    }

    @Nonnull
    public Setting<Boolean> getChatCompleter() {
        return chatCompleter;
    }

    @Nonnull
    public Setting<Boolean> getForgeSupport() {
        return forgeSupport;
    }

    @Nonnull
    public Setting<Boolean> getOptifineSupport() {
        return optifineSupport;
    }

    @Nonnull
    public Setting<Boolean> getVelocitySupport() {
        return velocitySupport;
    }

    @Nonnull
    public static Settings getInstance() {
        return instance;
    }
}
