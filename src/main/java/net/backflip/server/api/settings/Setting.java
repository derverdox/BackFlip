package net.backflip.server.api.settings;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Setting<S> {

    @Nonnull private static final List<Setting<?>> list = new ArrayList<>();

    @Nonnull
    private final String key;
    @Nonnull
    private S value;

    public Setting(@Nonnull String key, @Nonnull S value) {
        this.key = key;
        this.value = value;
        getList().add(this);
    }

    @Nonnull
    public String getKey() {
        return key;
    }

    @Nonnull
    public S getValue() {
        return value;
    }

    public void setValue(@Nonnull S value) {
        this.value = value;
    }

    @Nonnull
    public static List<Setting<?>> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting<?> setting = (Setting<?>) o;
        return key.equals(setting.key) && value.equals(setting.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
