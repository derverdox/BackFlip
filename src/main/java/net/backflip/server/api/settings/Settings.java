package net.backflip.server.api.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.backflip.server.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.*;

public class Settings {

    @Nonnull private static final Settings instance = new Settings();

    @Nonnull private final File file = new File("settings.json");
    @Nonnull public final Setting<String> VELOCITY_SECRET = new Setting<>("velocity-secret", "secret");
    @Nonnull public final Setting<String> HOST_ADDRESS = new Setting<>("host-address", "localhost");
    @Nonnull public final Setting<Boolean> AUTO_UPDATER = new Setting<>("auto-updater", true);
    @Nonnull public final Setting<Boolean> TAB_COMPLETER = new Setting<>("tab-completer", true);
    @Nonnull public final Setting<Boolean> CHAT_COMPLETER = new Setting<>("chat-completer", true);
    @Nonnull public final Setting<Boolean> MOJANG_AUTHENTICATION = new Setting<>("mojang-authentication", true);
    @Nonnull public final Setting<Boolean> FORGE_SUPPORT = new Setting<>("forge-support", true);
    @Nonnull public final Setting<Boolean> OPTIFINE_SUPPORT = new Setting<>("optifine-support", true);
    @Nonnull public final Setting<Boolean> VELOCITY_SUPPORT = new Setting<>("velocity-support", false);
    @Nonnull public final Setting<Integer> MAX_PLAYER_COUNT = new Setting<>("max-player-count", 100);
    @Nonnull public final Setting<Integer> PORT = new Setting<>("port", 25565);

    protected Settings() {
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Logger.info("Generated '" + getFile().getName() + "' file at '" + getFile().getAbsolutePath() + "'");
                } else {
                    Logger.error("Couldn't generate file '" + getFile().getName() + "' at '" + getFile().getAbsolutePath() + "'");
                    throw new FileNotFoundException("Couldn't generate file");
                }
                JsonObject jsonObject = new JsonObject();
                for (Setting<?> setting : Setting.getList()) {
                    if (setting.getValue() instanceof String) {
                        jsonObject.addProperty(setting.getKey(), ((String) setting.getValue()));
                    } else if (setting.getValue() instanceof Boolean) {
                        jsonObject.addProperty(setting.getKey(), ((Boolean) setting.getValue()));
                    } else if (setting.getValue() instanceof Integer) {
                        jsonObject.addProperty(setting.getKey(), ((Integer) setting.getValue()));
                    } else {
                        Logger.warn("Unset Setting Type '" + setting.getValue().getClass().getSimpleName() + "'");
                    }
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(jsonObject.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(file));
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                for (String key : jsonObject.keySet()) {
                    for (Setting<?> setting : Setting.getList()) {
                        if (setting.getKey().equals(key)) {
                            if (setting.getValue() instanceof String) {
                                ((Setting<String>) setting).setValue(jsonObject.get(key).getAsString());
                                Logger.info("Loaded setting value '" + setting.getValue() + "' from key '" + setting.getKey() + "'");
                            } else if (setting.getValue() instanceof Boolean) {
                                ((Setting<Boolean>) setting).setValue(jsonObject.get(key).getAsBoolean());
                                Logger.info("Loaded setting value '" + setting.getValue() + "' from key '" + setting.getKey() + "'");
                            } else if (setting.getValue() instanceof Integer) {
                                ((Setting<Integer>) setting).setValue(jsonObject.get(key).getAsInt());
                                Logger.info("Loaded setting value '" + setting.getValue() + "' from key '" + setting.getKey() + "'");
                            } else {
                                Logger.warn("Unset Setting Type '" + setting.getValue().getClass().getSimpleName() + "'");
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    public File getFile() {
        return file;
    }

    @Nonnull
    public static Settings getInstance() {
        return instance;
    }
}
