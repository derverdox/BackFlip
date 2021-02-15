package net.backflip.server.api.message;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.backflip.server.api.json.JsonBeautifier;
import net.backflip.server.api.json.JsonFile;
import net.backflip.server.api.logger.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    @Nonnull public static final ChatComponent LOG_INFO = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_INFO), "§8[§1%time% §8|§1 Info §8| §1%thread%§8]§r");
    @Nonnull public static final ChatComponent LOG_WARN = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_WARN), "§8[§e%time% §8|§e Warning §8| §e%thread%§8]§r");
    @Nonnull public static final ChatComponent LOG_ERROR = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_ERROR), "§8[§4%time% §8|§4 Error §8| §4%thread%§8]§r");
    @Nonnull public static final ChatComponent LOG_DEBUG = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.LOG_DEBUG), "§8[§6%time% §8|§6 Debug §8| §6%thread%§8]§r");
    @Nonnull public static final ChatComponent PREFIX = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.PREFIX), "§f§lBackFlip §8»");
    @Nonnull public static final ChatComponent MOTD = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.MOTD), "§7%version% §8» §eA Minestom server");
    @Nonnull public static final ChatComponent MOTD_VERSION = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.MOTD_VERSION), "§8» §f%version%");
    @Nonnull public static final ChatComponent SERVER_BRAND = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.SERVER_BRAND), "§8» §f%version%");

    @Nonnull public static final ChatComponent NO_PERMISSION_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.NO_PERMISSION), "§cYou have no Rights §8(§4%permission%§8)");
    @Nonnull public static final ChatComponent COMMAND_EXCEPTION_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.COMMAND_EXCEPTION), "§cAn error has occurred");
    @Nonnull public static final ChatComponent UNKNOWN_COMMAND_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.UNKNOWN_COMMAND), "§cThe Command §8(§4%command%§8)§c doesn't exist");
    @Nonnull public static final ChatComponent PLAYER_COMMAND_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.PLAYER_COMMAND), "§cThis is a player command");
    @Nonnull public static final ChatComponent CONSOLE_COMMAND_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.CONSOLE_COMMAND), "§cThis is a console command");
    @Nonnull public static final ChatComponent KICKED_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.KICKED), "§cYou got kicked§8: §4%reason%");
    @Nonnull public static final ChatComponent BANNED_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.BANNED), "§cYou got banned§8: §4%reason%");
    @Nonnull public static final ChatComponent FIRST_JOIN_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.FIRST_JOIN_MESSAGE), "§6%player%§a joined the game §8(§7the first time§8)");
    @Nonnull public static final ChatComponent JOIN_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.JOIN_MESSAGE), "§6%player%§a joined the game");
    @Nonnull public static final ChatComponent QUIT_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.QUIT_MESSAGE), "§4%player%§c left the game");
    @Nonnull public static final ChatComponent KICK_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.KICK_MESSAGE), "§4%player%§c left the game §8(§7got kicked§8)");
    @Nonnull public static final ChatComponent WORLD_SAVED_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.WORLD_SAVED), "§aSaved the world §6%world%");

    @Nonnull public static final ChatComponent NO_PERMISSION_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.NO_PERMISSION), "§cDarauf hast du keine Rechte §8(§4%permission%§8)");
    @Nonnull public static final ChatComponent COMMAND_EXCEPTION_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.COMMAND_EXCEPTION), "§cEin fehler is aufgetreten");
    @Nonnull public static final ChatComponent UNKNOWN_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.UNKNOWN_COMMAND), "§cDer Command §8(§4%command%§8)§c existiert nicht");
    @Nonnull public static final ChatComponent PLAYER_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.PLAYER_COMMAND), "§cDas ist ein Spieler command");
    @Nonnull public static final ChatComponent CONSOLE_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.CONSOLE_COMMAND), "§cDas ist ein Konsolen command");
    @Nonnull public static final ChatComponent KICKED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.KICKED), "§cDu wurdest gekickt§8: §4%reason%");
    @Nonnull public static final ChatComponent BANNED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.BANNED), "§cDu wurdest gebannt§8: §4%reason%");
    @Nonnull public static final ChatComponent FIRST_JOIN_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.FIRST_JOIN_MESSAGE), "§6%player%§a ist dem server beigetreten §8(§7zum ersten mal§8)");
    @Nonnull public static final ChatComponent JOIN_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.JOIN_MESSAGE), "§6%player%§a ist dem server beigetreten");
    @Nonnull public static final ChatComponent QUIT_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.QUIT_MESSAGE), "§4%player%§c hat den server verlassen");
    @Nonnull public static final ChatComponent KICK_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.KICK_MESSAGE), "§4%player%§c hat den server verlassen §8(§7gekickt§8)");
    @Nonnull public static final ChatComponent WORLD_SAVED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.WORLD_SAVED), "§aDie welt §6%world%§a wurde gespeichert");

    static {
        for (Language language : Language.values()) {
            try {
                JsonFile.create(language.getFile());
                JsonElement jsonElement = JsonParser.parseReader(new FileReader(language.getFile()));
                if (!(jsonElement instanceof JsonObject)) {
                    jsonElement = new JsonObject();
                }
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    for (ChatComponent component : ChatComponent.getMessages()) {
                        if (jsonObject.keySet().contains(component.getLanguageKey().getMessageKey().getKey())) {
                            String value = jsonObject.get(component.getLanguageKey().getMessageKey().getKey()).getAsString();
                            component.setText(value);
                            Logger.debug("§aLoaded component §8'§6" + component.getLanguageKey().getMessageKey().getKey() + "§8'§a with value §8'§6" + value + "§8'§6 for language §8'§r" + component.getLanguageKey().getLanguage().getName() + "§8'");
                        } else {
                            if (component.getLanguageKey().getLanguage().equals(language)) {
                                jsonObject.addProperty(component.getLanguageKey().getMessageKey().getKey(), component.getText());
                            }
                        }
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter(language.getFile()));
                    writer.write(JsonBeautifier.beautify(jsonObject.toString()));
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static ChatComponent valueOf(@Nonnull LanguageKey languageKey) {
        for (ChatComponent message : ChatComponent.getMessages()) {
            if (message.getLanguageKey().equals(languageKey)) {
                return message;
            }
        }
        return null;
    }
}
