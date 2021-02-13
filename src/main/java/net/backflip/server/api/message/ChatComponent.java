package net.backflip.server.api.message;

import net.kyori.adventure.text.Component;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatComponent {

    @Nonnull private static final List<ChatComponent> messages = new ArrayList<>();

    @Nonnull private final LanguageKey languageKey;
    @Nonnull private Component component = Component.text("");
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
    public Component getComponent() {
        return component;
    }

    @Nonnull
    public String getText() {
        return text;
    }

    private void setComponent(@Nonnull Component component) {
        this.component = component;
    }

    public void setText(@Nonnull String text) {
        this.text = text;
        setComponent(Component.text(text));
    }

    @Override
    public String toString() {
        return "ChatComponent{" +
                "languageKey=" + languageKey +
                ", component=" + component +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatComponent that = (ChatComponent) o;
        return languageKey.equals(that.languageKey) && component.equals(that.component) && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageKey, component, text);
    }

    @Nonnull
    public static List<ChatComponent> getMessages() {
        return messages;
    }
}
