package net.backflip.server.api.message;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatComponent {

    @Nonnull private static final List<ChatComponent> messages = new ArrayList<>();

    @Nonnull private final LanguageKey languageKey;
    @Nonnull private String text = "";

    public ChatComponent(@Nonnull LanguageKey languageKey, @Nonnull String text) {
        this.languageKey = languageKey;
        setText(text);
        getMessages().add(this);
    }

    @Nonnull
    public LanguageKey getLanguageKey() {
        return languageKey;
    }

    @Nonnull
    public String getText() {
        return text;
    }

    public void setText(@Nonnull String text) {
        this.text = text;
    }

    @Nonnull
    public String getText(@Nonnull Placeholder... placeholders) {
        String text = this.text;
        for (Placeholder placeholder : placeholders) {
            text = text.replace("%" + placeholder.getPlaceholder() + "%", placeholder.getObject().toString());
        }
        return text;
    }

    @Override
    public String toString() {
        return "ChatComponent{" +
                "languageKey=" + languageKey +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatComponent component = (ChatComponent) o;
        return languageKey.equals(component.languageKey) && text.equals(component.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageKey, text);
    }

    @Nonnull
    public static List<ChatComponent> getMessages() {
        return messages;
    }
}
