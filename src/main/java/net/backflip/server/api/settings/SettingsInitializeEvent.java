package net.backflip.server.api.settings;

import net.backflip.server.api.logger.Logger;
import net.minestom.server.event.Event;

public class SettingsInitializeEvent extends Event {

    public SettingsInitializeEvent() {
        Logger.debug("§aInitializing Settings");
    }
}
