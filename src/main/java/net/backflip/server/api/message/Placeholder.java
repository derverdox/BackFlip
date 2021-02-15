package net.backflip.server.api.message;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class Placeholder {

    @Nonnull private final String placeholder;
    @Nonnull private final Object object;

    public Placeholder(@Nonnull String placeholder, @Nullable Object object) {
        this.placeholder = placeholder;
        this.object = (object == null ? "§8-§7/§8-" : object);
    }

    @Nonnull
    public String getPlaceholder() {
        return placeholder;
    }

    @Nonnull
    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Placeholder{" +
                "placeholder='" + placeholder + '\'' +
                ", object=" + object +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placeholder that = (Placeholder) o;
        return placeholder.equals(that.placeholder) && object.equals(that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeholder, object);
    }
}
