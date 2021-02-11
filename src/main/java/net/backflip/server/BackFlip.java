package net.backflip.server;

import net.backflip.server.annotations.*;
import net.backflip.server.api.plugin.*;
import net.backflip.server.enumerations.Month;
import net.backflip.server.world.WorldManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.optifine.OptifineSupport;

import javax.annotation.Nonnull;
import java.util.UUID;

@Extension(name = "BackFlip",
        description = "default backflip extension",
        version = "0.1",
        credits = @Links(),
        contributors = @Contributors(contributors = {
                @Contributor(firstname = "Lukas", lastname = "Jonsson", pseudonym = "Verdox", contact = @Contact(mail = "", website = @URL(url = "https://yourserverpromo.de"), discord = "Verdox#2347")),
                @Contributor(firstname = "David", lastname = "Kirschner", pseudonym = "NonSwag", age = 17, birthday = @Date(day = 17, month = Month.SEPTEMBER, year = 2003), contact = @Contact(mail = "kirschner.david@thenextlvl.net", website = @URL(url = "https://www.thenextlvl.net"), discord = "NonSwag#8443", twitter = "@OfficialNonSwag"))}),
        links = @Links(download = @URL(url = ""), github = @URL(url = ""), gitlab = @URL(url = ""), source = @URL(url = ""), website = @URL(url = "")))
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
     ----------
     » Eigene Plugin Schnittstelle? Oder lieber das Minestom Erweiterungssystem nutzen? (jep eigene aber mit annotations und nicht sowas wie extends JavaPlugin)
     » @Extension(String name, String version, String website, String description) -> für die infos im extension manager
     » @Inject -> zum kennzeichnen der main method -> throws InjectException
     » "backflip-extension.json" zum registrieren der main class -> { "main": "net.example.server.Extension" }
     » hatte langeweile schau dir mal die plugin annotations an :D
     ----------
     » Vanilla kram reimplementieren
     */

    @Nonnull
    private static final String version = "BackFlip v0.1";

    @Nonnull
    private static BackFlip instance;

    @Nonnull
    public static BackFlip getInstance() {
        return instance;
    }

    @Nonnull
    public static String getVersion() {
        return version;
    }

    @Inject
    public static void main(String[] args) {
        BackFlip backFlip = new BackFlip(true,true);
        backFlip.startServer();
    }

    private final MinecraftServer minecraftServer;
    private final boolean enableMojangAuth;
    private final boolean enableOptifineSupport;
    private final WorldManager worldManager;

    protected BackFlip(boolean enableMojangAuth, boolean enableOptifineSupport){
        this.minecraftServer = MinecraftServer.init();
        this.enableMojangAuth = enableMojangAuth;
        this.enableOptifineSupport = enableOptifineSupport;
        this.worldManager = new WorldManager(this);
    }

    void startServer(){

        if(enableMojangAuth)
            MojangAuth.init();
        if(enableOptifineSupport)
            OptifineSupport.enable();

        minecraftServer.start("localhost", 25565, (playerConnection, responseData) -> {
            responseData.setMaxPlayer(0);
            responseData.addPlayer("made by", UUID.randomUUID());
            responseData.addPlayer("NonSwag", UUID.randomUUID());
            responseData.addPlayer("Verdox", UUID.randomUUID());
            responseData.setDescription(getVersion());
        });
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }
}
