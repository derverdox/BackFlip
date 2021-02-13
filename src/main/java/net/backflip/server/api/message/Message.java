package net.backflip.server.api.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.backflip.server.api.json.JsonBeautifier;
import net.backflip.server.api.json.JsonFile;
import net.backflip.server.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    @Nonnull public static final ChatComponent LOG_INFO = new ChatComponent(new LanguageKey(Language.ROOT, "log-info"), "§8[§1%time% §8|§1 Info §8| §1%thread%§8]§r");
    @Nonnull public static final ChatComponent LOG_WARN = new ChatComponent(new LanguageKey(Language.ROOT, "log-warn"), "§8[§e%time% §8|§e Warning §8| §e%thread%§8]§r");
    @Nonnull public static final ChatComponent LOG_ERROR = new ChatComponent(new LanguageKey(Language.ROOT, "log-error"), "§8[§4%time% §8|§4 Error §8| §4%thread%§8]§r");
    @Nonnull public static final ChatComponent LOG_DEBUG = new ChatComponent(new LanguageKey(Language.ROOT, "log-debug"), "§8[§6%time% §8|§6 Debug §8| §6%thread%§8]§r");
    @Nonnull public static final ChatComponent PREFIX = new ChatComponent(new LanguageKey(Language.ROOT, "prefix"), "§f§lBackFlip §8»");
    @Nonnull public static final ChatComponent NO_PERMISSION = new ChatComponent(new LanguageKey(Language.ENGLISH, "no-permission"), "§cYou have no Rights §8(§4%permission%§8)");
    @Nonnull public static final ChatComponent COMMAND_EXCEPTION = new ChatComponent(new LanguageKey(Language.ENGLISH, "command-exception"), "§cAn error has occurred");
    @Nonnull public static final ChatComponent UNKNOWN_COMMAND = new ChatComponent(new LanguageKey(Language.ENGLISH, "unknown-command"), "§cThe Command §8(§4%command%§8)§c doesn't exist");
    @Nonnull public static final ChatComponent PLAYER_COMMAND = new ChatComponent(new LanguageKey(Language.ENGLISH, "player-command"), "§cThis is a Player command");
    @Nonnull public static final ChatComponent CONSOLE_COMMAND = new ChatComponent(new LanguageKey(Language.ENGLISH, "player-command"), "§cThis is a Console command");
    @Nonnull public static final ChatComponent KICKED = new ChatComponent(new LanguageKey(Language.ENGLISH, "kicked"), "§cYou got kicked");
    @Nonnull public static final ChatComponent BANNED = new ChatComponent(new LanguageKey(Language.ENGLISH, "banned"), "§cYou got banned");
    @Nonnull public static final ChatComponent FIRST_JOIN_MESSAGE = new ChatComponent(new LanguageKey(Language.ENGLISH, "first-join-message"), "§6%player%§a joined the game §8(§7the first time§8)");
    @Nonnull public static final ChatComponent JOIN_MESSAGE = new ChatComponent(new LanguageKey(Language.ENGLISH, "join-message"), "§6%player%§a joined the game");
    @Nonnull public static final ChatComponent QUIT_MESSAGE = new ChatComponent(new LanguageKey(Language.ENGLISH, "quit-message"), "§4%player%§c left the game");
    @Nonnull public static final ChatComponent KICK_MESSAGE = new ChatComponent(new LanguageKey(Language.ENGLISH, "kick-message"), "§4%player%§c left the game §8(§7got kicked§8)");

    static {
        for (Language language : Language.values()) {
            try {
                JsonFile.create(language.getFile());
                JsonElement jsonElement = JsonParser.parseReader(new FileReader(language.getFile()));
                if (!(jsonElement instanceof JsonObject)) {
                    jsonElement = new JsonObject();
                }
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject().deepCopy();
                    for (ChatComponent component : ChatComponent.getMessages()) {
                        if (jsonObject.keySet().contains(component.getLanguageKey().getKey())) {
                            String value = jsonObject.get(component.getLanguageKey().getKey()).getAsString();
                            component.setText(value);
                            Logger.debug("§aLoaded component §8'§6" + component.getLanguageKey().getKey() + "§8'§a with value §8'§6" + value + "§8'§6 for language §8'§r" + component.getLanguageKey().getLanguage().getName() + "§8'");
                        } else {
                            if (component.getLanguageKey().getLanguage().equals(language)) {
                                jsonObject.addProperty(component.getLanguageKey().getKey(), component.getText());
                            }
                        }
                    }
                    if (!jsonElement.getAsJsonObject().equals(jsonObject)) {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(language.getFile()));
                        writer.write(JsonBeautifier.beautify(jsonObject.toString()));
                        writer.close();
                        Logger.debug("§aUpdated and saved file §8'§6" + language.getFile().getAbsolutePath() + "§8'");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
