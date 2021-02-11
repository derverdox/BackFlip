package net.backflip.server;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.optifine.OptifineSupport;

import javax.annotation.Nonnull;
import java.util.UUID;

public class BackFlip {

    /*
    TODO:
     » MetaData Interface (PersistentData)
     » LanguageAPI (Übersetzungssystem)
     » ChatAPI (Einheitliches Design)
     » Main Method Settings Loader (mach ich)
     » Tab API voll ausnutzen
     » CustomModelData für custom Resource Pack kram und so :D
     ----------
     » AnvilChunkLoader (Storage System) (Blöcke, TileEntities, Entities) (Hephaistos MCA -> MinestomVanillaReimplementation)
     » WeltGenerator (Werden wir save nicht selber schreiben, aber es gibt bereits ein paar gute für Minestom soweit ich weis :D
     » Eigenes BlockState System -> Das von Minestom ist bisschen unübersichtlich
     » Eigenes WorldManager System (Chunks laden ; Entladen ; Ist sowieso Performant weil Minestom nur die Frage ist wann :D-> Minestom Events)
     » Eigenes Event System? Oder lieber das von Minestom erweitern (TNLEventAPI)
     » Für Commands auch das Minestom Command System (TNLCommandAPI)
     » Eigene Plugin Schnittstelle? Oder lieber das Minestom Erweiterungssystem nutzen? (Vermutlich eigene damit die User eher mit unserer API arbeiten)
     ----------
     » Vanilla kram reimplementieren
     */

    @Nonnull
    private static final String version = "BackFlip v0.1";

    @Nonnull
    public static String getVersion() {
        return version;
    }

    public static void main(String[] args) {

        MinecraftServer minecraftServer = MinecraftServer.init();
        MinecraftServer.setBrandName(getVersion());
        MojangAuth.init();
        OptifineSupport.enable();

        // TODO: Properties File generieren bzw auslesen? nope json file <3 (org.json)

        minecraftServer.start("localhost", 25565, (playerConnection, responseData) -> {
            responseData.setMaxPlayer(0);
            responseData.addPlayer("made by", UUID.randomUUID());
            responseData.addPlayer("NonSwag", UUID.randomUUID());
            responseData.addPlayer("Verdox", UUID.randomUUID());
            responseData.setDescription(getVersion());
        });
    }
}
