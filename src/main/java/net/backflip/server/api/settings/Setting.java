package net.backflip.server.api.settings;

import javax.annotation.Nullable;

public class Setting<S> {

    @Nullable
    private S value;

    public Setting(@Nullable S value) {
        this.value = value;
    }

    @Nullable
    public S getValue() {
        return value;
    }

    public void setValue(@Nullable S value) {
        this.value = value;
    }
}
