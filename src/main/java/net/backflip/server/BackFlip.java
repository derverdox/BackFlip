package net.backflip.server;

import net.backflip.server.annotations.*;
import net.backflip.server.api.extension.*;
import net.backflip.server.api.logger.Logger;
import net.backflip.server.api.settings.Setting;
import net.backflip.server.api.settings.Settings;
import net.backflip.server.commands.SaveAllCommand;
import net.backflip.server.commands.StopCommand;
import net.backflip.server.commands.TestCommand;
import net.backflip.server.enumerations.Month;
import net.backflip.server.world.generator.WorldGenerator;
import net.backflip.server.world.WorldManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.utils.Position;
import net.minestom.server.world.DimensionType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Extension(name = "BackFlip",
        description = "default backflip extension",
        version = "0.1",
        credits = @Links(),
        contributors = {
                @Contributor(firstname = "Lukas", lastname = "Jonsson", pseudonym = "Verdox", contact = @Contact(website = @URL(url = "https://yourserverpromo.de"), discord = "Verdox#2347")),
                @Contributor(firstname = "David", lastname = "Kirschner", pseudonym = "NonSwag", age = 17, birthday = @Date(day = 17, month = Month.SEPTEMBER, year = 2003), contact = @Contact(mail = "kirschner.david@thenextlvl.net", website = @URL(url = "https://www.thenextlvl.net"), discord = "NonSwag#8443", twitter = "@OfficialNonSwag"))},
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
    private static final BackFlip instance = new BackFlip();

    @Nonnull
    public static BackFlip getInstance() {
        return instance;
    }

    @Nonnull
    public String getVersion() {
        return "BackFlip v0.1";
    }

    @Nonnull private final MinecraftServer server;
    @Nonnull private final WorldManager worldManager;
    @Nonnull private final Settings settings = Settings.getInstance();

    @Nonnull
    public MinecraftServer getServer() {
        return server;
    }

    @Nonnull
    public WorldManager getWorldManager() {
        return worldManager;
    }

    @Nonnull
    public Settings getSettings() {
        return settings;
    }

    protected BackFlip() {
        this.server = MinecraftServer.init();
        this.worldManager = new WorldManager(getInstance(), 6);
        if (getSettings().MOJANG_AUTHENTICATION.getValue()) {
            MojangAuth.init();
        }
        if (getSettings().OPTIFINE_SUPPORT.getValue()) {
            OptifineSupport.enable();
        }
        if (getSettings().VELOCITY_SUPPORT.getValue()) {
            VelocityProxy.enable(getSettings().VELOCITY_SECRET.getValue());
        }
        Instance instance = worldManager.loadOrCreateWorld("testWelt", DimensionType.OVERWORLD);
        //InstanceContainer instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();
        //instanceContainer.setChunkGenerator(new WorldGenerator());
        //instanceContainer.enableAutoChunkLoad(true);
        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        connectionManager.addPlayerInitialization(player -> {
            player.addEventCallback(PlayerLoginEvent.class, event -> event.setSpawningInstance(instance));
            player.addEventCallback(PlayerSpawnEvent.class, event -> player.teleport(new Position(0, 45, 0)));
            player.setGameMode(GameMode.CREATIVE);
        });
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent.class,playerLoginEvent -> {
            playerLoginEvent.getPlayer().setFlying(true);
            playerLoginEvent.getPlayer().setFlyingSpeed(1);
        });

        MinecraftServer.getCommandManager().register(new StopCommand());
        MinecraftServer.getCommandManager().register(new SaveAllCommand());
        MinecraftServer.getCommandManager().register(new TestCommand());
    }

    protected void start(String... args) {
        for (String arg : args) {
            String[] split = arg.split(":");
            if (split.length >= 2) {
                String key = split[0].substring(1);
                List<String> values = new ArrayList<>(Arrays.asList(split));
                values.remove(0);
                String value = String.join(":", values);
                for (Setting<?> setting : Setting.getList()) {
                    if (key.equalsIgnoreCase(setting.getKey())) {
                        try {
                            if (setting.getValue() instanceof String) {
                                ((Setting<String>) setting).setValue(value);
                                Logger.info("Loaded setting '" + key + "' with value '" + value + "'");
                            } else if (setting.getValue() instanceof Boolean) {
                                ((Setting<Boolean>) setting).setValue(Boolean.parseBoolean(value));
                                Logger.info("Loaded setting '" + key + "' with value '" + value + "'");
                            } else if (setting.getValue() instanceof Integer) {
                                ((Setting<Integer>) setting).setValue(Integer.parseInt(value));
                                Logger.info("Loaded setting '" + key + "' with value '" + value + "'");
                            } else {
                                Logger.warn("Unset Setting Type '" + setting.getValue().getClass().getSimpleName() + "'");
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
        }
        getServer().start(getSettings().HOST_ADDRESS.getValue(), getSettings().PORT.getValue(), (playerConnection, responseData) -> {
            responseData.setMaxPlayer(getSettings().MAX_PLAYER_COUNT.getValue());
            responseData.addPlayer("made by", UUID.randomUUID());
            responseData.addPlayer("NonSwag", UUID.randomUUID());
            responseData.addPlayer("Verdox", UUID.randomUUID());
            responseData.setDescription(getVersion());
        });
        Logger.info("Started server on '" + getSettings().HOST_ADDRESS.getValue() + ":" + getSettings().PORT.getValue() + "'");
    }
}
