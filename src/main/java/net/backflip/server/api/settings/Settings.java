package net.backflip.server.api.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.backflip.server.api.json.JsonBeautifier;
import net.backflip.server.api.json.JsonFile;
import net.backflip.server.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.*;

public class Settings {

    @Nonnull private static final File file = new File("settings.json");
    @Nonnull public static final Setting<String> VELOCITY_SECRET = new Setting<>("velocity-secret", "secret");
    @Nonnull public static final Setting<String> HOST_ADDRESS = new Setting<>("host-address", "localhost");
    @Nonnull public static final Setting<Boolean> AUTO_UPDATER = new Setting<>("auto-updater", true);
    @Nonnull public static final Setting<Boolean> TAB_COMPLETER = new Setting<>("tab-completer", true);
    @Nonnull public static final Setting<Boolean> CHAT_COMPLETER = new Setting<>("chat-completer", true);
    @Nonnull public static final Setting<Boolean> MOJANG_AUTHENTICATION = new Setting<>("mojang-authentication", true);
    @Nonnull public static final Setting<Boolean> FORGE_SUPPORT = new Setting<>("forge-support", true);
    @Nonnull public static final Setting<Boolean> OPTIFINE_SUPPORT = new Setting<>("optifine-support", true);
    @Nonnull public static final Setting<Boolean> VELOCITY_SUPPORT = new Setting<>("velocity-support", false);
    @Nonnull public static final Setting<Boolean> DEBUG = new Setting<>("debug", true);
    @Nonnull public static final Setting<Boolean> JOIN_MESSAGE = new Setting<>("join-message", true);
    @Nonnull public static final Setting<Boolean> QUIT_MESSAGE = new Setting<>("quit-message", true);
    @Nonnull public static final Setting<Boolean> KICK_MESSAGE = new Setting<>("kick-message", true);
    @Nonnull public static final Setting<Integer> MAX_PLAYER_COUNT = new Setting<>("max-player-count", 100);
    @Nonnull public static final Setting<Integer> PORT = new Setting<>("port", 25565);

    static {
        try {
            JsonFile.create(getFile());
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(getFile()));
            if (!(jsonElement instanceof JsonObject)) {
                jsonElement = new JsonObject();
            }
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject().deepCopy();
                for (Setting<?> setting : Setting.getList()) {
                    if (jsonObject.keySet().contains(setting.getKey())) {
                        if (setting.getValue() instanceof String) {
                            String value = jsonObject.get(setting.getKey()).getAsString();
                            ((Setting<String>) setting).setValue(value);
                            Logger.debug("§aLoaded setting §8'§6" + setting.getKey() + "§8'§a with value §8'§6" + value + "§8'");
                        } else if (setting.getValue() instanceof Boolean) {
                            boolean value = jsonObject.get(setting.getKey()).getAsBoolean();
                            ((Setting<Boolean>) setting).setValue(value);
                            Logger.debug("§aLoaded setting §8'§6" + setting.getKey() + "§8'§a with value §8'§6" + value + "§8'");
                        } else if (setting.getValue() instanceof Integer) {
                            int value = jsonObject.get(setting.getKey()).getAsInt();
                            ((Setting<Integer>) setting).setValue(value);
                            Logger.debug("§aLoaded setting §8'§6" + setting.getKey() + "§8'§a with value §8'§6" + value + "§8'");
                        } else {
                            Logger.warn("§cUnset Setting Type §8§4'" + setting.getValue().getClass().getSimpleName() + "§8'");
                        }
                    } else {
                        if (setting.getValue() instanceof String) {
                            String value = (String) setting.getValue();
                            jsonObject.addProperty(setting.getKey(), value);
                            Logger.debug("§aAdded setting §8'§6" + setting.getKey() + "§8'§a with value §8'§6" + value + "§8'");
                        } else if (setting.getValue() instanceof Boolean) {
                            Boolean value = (Boolean) setting.getValue();
                            jsonObject.addProperty(setting.getKey(), value);
                            Logger.debug("§aAdded setting §8'§6" + setting.getKey() + "§8'§a with value §8'§6" + value + "§8'");
                        } else if (setting.getValue() instanceof Integer) {
                            Integer value = (Integer) setting.getValue();
                            jsonObject.addProperty(setting.getKey(), value);
                            Logger.debug("§aAdded setting §8'§6" + setting.getKey() + "§8'§a with value §8'§6" + value + "§8'");
                        } else {
                            Logger.warn("§cUnset Setting Type §8§4'" + setting.getValue().getClass().getSimpleName() + "§8'");
                        }
                    }
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()));
                writer.write(JsonBeautifier.beautify(jsonObject.toString()));
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    public static File getFile() {
        return file;
    }
}
