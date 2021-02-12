package net.backflip.server.api.settings;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
}
