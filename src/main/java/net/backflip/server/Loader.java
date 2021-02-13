package net.backflip.server;

import net.backflip.server.api.message.Message;
import net.backflip.server.api.settings.Settings;

public class Loader {

    public static void main(String[] args) {
        BackFlip.getInstance().start(args);
    }
}
