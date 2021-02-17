package net.backflip.server.api.message;

import javax.annotation.Nonnull;
import java.util.Objects;

public class MessageKey {
    @Nonnull public static MessageKey LOG_INFO = new MessageKey("log-info");
    @Nonnull public static MessageKey LOG_WARN = new MessageKey("log-warn");
    @Nonnull public static MessageKey LOG_ERROR = new MessageKey("log-error");
    @Nonnull public static MessageKey LOG_DEBUG = new MessageKey("log-debug");
    @Nonnull public static MessageKey CHAT_FORMAT = new MessageKey("chat-format");
    @Nonnull public static MessageKey PREFIX = new MessageKey("prefix");
    @Nonnull public static MessageKey MOTD = new MessageKey("motd");
    @Nonnull public static MessageKey MOTD_VERSION = new MessageKey("motd-version");
    @Nonnull public static MessageKey SERVER_BRAND = new MessageKey("server-brand");

    @Nonnull public static MessageKey NO_PERMISSION = new MessageKey("no-permission");
    @Nonnull public static MessageKey COMMAND_EXCEPTION = new MessageKey("command-exception");
    @Nonnull public static MessageKey UNKNOWN_COMMAND = new MessageKey("unknown-command");
    @Nonnull public static MessageKey PLAYER_COMMAND = new MessageKey("player-command");
    @Nonnull public static MessageKey CONSOLE_COMMAND = new MessageKey("console-command");
    @Nonnull public static MessageKey KICKED = new MessageKey("kicked");
    @Nonnull public static MessageKey BANNED = new MessageKey("banned");
    @Nonnull public static MessageKey FIRST_JOIN_MESSAGE = new MessageKey("first-join-message");
    @Nonnull public static MessageKey JOIN_MESSAGE = new MessageKey("join-message");
    @Nonnull public static MessageKey QUIT_MESSAGE = new MessageKey("quit-message");
    @Nonnull public static MessageKey WORLD_SAVED = new MessageKey("world-saved");
    @Nonnull public static MessageKey CHANGED_GAMEMODE = new MessageKey("changed-gamemode");
    @Nonnull public static MessageKey PLAYER_NOT_ONLINE = new MessageKey("player-not-online");
    @Nonnull public static MessageKey NOT_A_PLAYER = new MessageKey("not-a-player");

    @Nonnull private final String key;

    public MessageKey(@Nonnull String key) {
        this.key = key;
    }

    @Nonnull
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "MessageKey{" +
                "key='" + key + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageKey that = (MessageKey) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
