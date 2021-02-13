package net.backflip.server.api.json;

import net.backflip.server.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.*;

public class JsonFile {

    public static void create(@Nonnull File file) throws IOException {
        if (!file.exists()) {
            if (file.createNewFile()) {
                Logger.debug("§aGenerated file §8'§6" + file.getAbsolutePath() + "§8'");
            } else {
                Logger.error("§cCouldn't generate file §8'§4" + file.getAbsolutePath() + "§8'");
                throw new FileNotFoundException("Couldn't generate file");
            }
        }
    }
}
