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
    @Nonnull public static final ChatComponent CHAT_FORMAT = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.CHAT_FORMAT), "§8[§f%world%§8] §f%player% §8» §f%message%");
    @Nonnull public static final ChatComponent PREFIX = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.PREFIX), "§lBackFlip §8»");
    @Nonnull public static final ChatComponent MOTD = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.MOTD), "§7%version% §8» §eA Minestom server");
    @Nonnull public static final ChatComponent MOTD_VERSION = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.MOTD_VERSION), "§8» §f%version%");
    @Nonnull public static final ChatComponent SERVER_BRAND = new ChatComponent(new LanguageKey(Language.ROOT, MessageKey.SERVER_BRAND), "§8» §f%version%§7");

    @Nonnull public static final ChatComponent NO_PERMISSION_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.NO_PERMISSION), "§4%prefix%§c You have no Rights §8(§4%permission%§8)");
    @Nonnull public static final ChatComponent COMMAND_EXCEPTION_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.COMMAND_EXCEPTION), "§4%prefix%§c An error has occurred");
    @Nonnull public static final ChatComponent UNKNOWN_COMMAND_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.UNKNOWN_COMMAND), "§4%prefix%§c The Command §8(§4/%command%§8)§c doesn't exist");
    @Nonnull public static final ChatComponent PLAYER_COMMAND_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.PLAYER_COMMAND), "§4%prefix%§c This is a player command");
    @Nonnull public static final ChatComponent CONSOLE_COMMAND_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.CONSOLE_COMMAND), "§4%prefix%§c This is a console command");
    @Nonnull public static final ChatComponent KICKED_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.KICKED), "§4%prefix% §cYou got kicked%nl%§4%reason%");
    @Nonnull public static final ChatComponent BANNED_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.BANNED), "§4%prefix% §cYou got banned%nl%§4%reason%");
    @Nonnull public static final ChatComponent FIRST_JOIN_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.FIRST_JOIN_MESSAGE), "§6%prefix%§6 %player%§a joined the game §8(§7the first time§8)");
    @Nonnull public static final ChatComponent JOIN_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.JOIN_MESSAGE), "§6%prefix%§6 %player%§a joined the game");
    @Nonnull public static final ChatComponent QUIT_MESSAGE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.QUIT_MESSAGE), "§4%prefix%§4 %player%§c left the game");
    @Nonnull public static final ChatComponent WORLD_SAVED_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.WORLD_SAVED), "§6%prefix%§a Saved the world §6%world%");
    @Nonnull public static final ChatComponent CHANGED_GAMEMODE_EN = new ChatComponent(new LanguageKey(Language.ENGLISH, MessageKey.WORLD_SAVED), "§6%prefix%§a Your gamemode is now §6%gamemode%");

    @Nonnull public static final ChatComponent NO_PERMISSION_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.NO_PERMISSION), "§4%prefix%§c Darauf hast du keine Rechte §8(§4%permission%§8)");
    @Nonnull public static final ChatComponent COMMAND_EXCEPTION_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.COMMAND_EXCEPTION), "§4%prefix%§c Ein fehler is aufgetreten");
    @Nonnull public static final ChatComponent UNKNOWN_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.UNKNOWN_COMMAND), "§4%prefix%§c Der Command §8(§4/%command%§8)§c existiert nicht");
    @Nonnull public static final ChatComponent PLAYER_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.PLAYER_COMMAND), "§4%prefix%§c Das ist ein Spieler command");
    @Nonnull public static final ChatComponent CONSOLE_COMMAND_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.CONSOLE_COMMAND), "§4%prefix%§c Das ist ein Konsolen command");
    @Nonnull public static final ChatComponent KICKED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.KICKED), "§4%prefix% §cDu wurdest gekickt%nl%§4%reason%");
    @Nonnull public static final ChatComponent BANNED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.BANNED), "§4%prefix% §cDu wurdest gebannt%nl%§4%reason%");
    @Nonnull public static final ChatComponent FIRST_JOIN_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.FIRST_JOIN_MESSAGE), "§6%prefix%§6 %player%§a ist dem server beigetreten §8(§7zum ersten mal§8)");
    @Nonnull public static final ChatComponent JOIN_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.JOIN_MESSAGE), "§6%prefix%§6 %player%§a ist dem server beigetreten");
    @Nonnull public static final ChatComponent QUIT_MESSAGE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.QUIT_MESSAGE), "§4%prefix%§4 %player%§c hat den server verlassen");
    @Nonnull public static final ChatComponent WORLD_SAVED_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.WORLD_SAVED), "§6%prefix%§a Die welt §6%world%§a wurde gespeichert");
    @Nonnull public static final ChatComponent CHANGED_GAMEMODE_DE = new ChatComponent(new LanguageKey(Language.GERMAN, MessageKey.WORLD_SAVED), "§6%prefix%§a Dein gamemode ist jetzt §6%gamemode%");

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
                        if (component.getLanguageKey().getLanguage().equals(language)) {
                            if (jsonObject.keySet().contains(component.getLanguageKey().getMessageKey().getKey())) {
                                String value = jsonObject.get(component.getLanguageKey().getMessageKey().getKey()).getAsString();
                                component.setText(value);
                                Logger.debug("§aLoaded component §8'§6" + component.getLanguageKey().getMessageKey().getKey() + "§8'§a with value §8'§6" + value + "§8'§6 for language §8'§r" + component.getLanguageKey().getLanguage().getName() + "§8'");
                            } else {
                                if (component.getLanguageKey().getLanguage().equals(language)) {
                                    jsonObject.addProperty(component.getLanguageKey().getMessageKey().getKey(), component.getText());
                                    Logger.debug("§aAdded component §8'§6" + component.getLanguageKey().getMessageKey().getKey() + "§8'§a with value §8'§6" + component.getText() + "§8'§6 for language §8'§r" + component.getLanguageKey().getLanguage().getName() + "§8'");
                                }
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
