package net.backflip.server;

import net.backflip.server.annotations.*;
import net.backflip.server.api.event.Listener;
import net.backflip.server.api.extension.*;
import net.backflip.server.api.logger.Logger;
import net.backflip.server.api.message.Message;
import net.backflip.server.api.message.Placeholder;
import net.backflip.server.api.settings.Settings;
import net.backflip.server.commands.*;
import net.backflip.server.enumerations.Month;
import net.backflip.server.listeners.*;
import net.backflip.server.world.generator.WorldGenerator;
import net.backflip.server.world.WorldManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
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
     » variable storage api
     » permission storage
     ----------
     » MetaData Interface (PersistentData)
     » Tab API voll ausnutzen
     » CustomModelData für custom Resource Pack kram und so :D
     ----------
     » AnvilChunkLoader (Storage System) (Blöcke, TileEntities, Entities) (Hephaistos MCA -> MinestomVanillaReimplementation)
     » WeltGenerator (Werden wir save nicht selber schreiben, aber es gibt bereits ein paar gute für Minestom soweit ich weis :D
     » Eigenes BlockState System -> Das von Minestom ist bisschen unübersichtlich
     » Eigenes WorldManager System (Chunks laden ; Entladen ; Ist sowieso Performant weil Minestom nur die Frage ist wann :D-> Minestom Events)
     » Vanilla kram reimplementieren
     ----------
     » Eigene Plugin Schnittstelle? Oder lieber das Minestom Erweiterungssystem nutzen? (jep eigene aber mit annotations und nicht sowas wie extends JavaPlugin)
     » @Extension(String name, String version, String website, String description) -> für die infos im extension manager
     » @Inject -> zum kennzeichnen der main method -> throws InjectException
     » "backflip-extension.json" zum registrieren der main class -> { "main": "net.example.server.Extension" }
     */

    @Nonnull
    private static final BackFlip instance = new BackFlip();

    @Nonnull
    private final List<Listener> listeners = new ArrayList<>();
    @Nonnull
    private final MinecraftServer server;
    @Nonnull
    private final WorldManager worldManager;

    @Nonnull
    public static BackFlip getInstance() {
        return instance;
    }

    @Nonnull
    private List<Listener> getListeners() {
        return listeners;
    }

    @Nonnull
    public MinecraftServer getServer() {
        return server;
    }

    @Nonnull
    public WorldManager getWorldManager() {
        return worldManager;
    }

    @Nonnull
    public String getVersion() {
        return "BackFlip v0.1";
    }

    public static void main(String[] args) {
        getInstance().start(args);
    }

    protected BackFlip() {
        Placeholder.Registry.register(new Placeholder("version", getVersion()));
        Logger.info("§aStarting Backflip Server §8'§6%version%§8'");
        this.server = MinecraftServer.init();
        this.worldManager = new WorldManager(getInstance(), 6);
        if (Settings.MOJANG_AUTHENTICATION.getValue()) {
            MojangAuth.init();
        }
        if (Settings.OPTIFINE_SUPPORT.getValue()) {
            OptifineSupport.enable();
        }
        if (Settings.VELOCITY_SUPPORT.getValue()) {
            VelocityProxy.enable(Settings.VELOCITY_SECRET.getValue());
        }
        registerCommands();
        registerEvents();
        load();
    }

    protected void registerCommands() {
        registerCommand(new DifficultyCommand());
        registerCommand(new GameModeCommand());
        registerCommand(new SaveAllCommand());
        registerCommand(new StopCommand());
    }

    protected void registerEvents() {
        addEventListener(new SettingsListener());
        addEventListener(new CommandListener());
        addEventListener(new ChatListener());
        addEventListener(new JoinListener());
        addEventListener(new QuitListener());
    }

    protected void load() {
        Instance instance = worldManager.loadOrCreateWorld("testWelt", DimensionType.OVERWORLD);
        InstanceContainer instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();
        instanceContainer.setChunkGenerator(new WorldGenerator());
        instanceContainer.enableAutoChunkLoad(true);
        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        connectionManager.addPlayerInitialization(player -> {
            player.addEventCallback(PlayerLoginEvent.class, event -> event.setSpawningInstance(instance));
            player.addEventCallback(PlayerSpawnEvent.class, event -> player.teleport(new Position(0, 140, 0)));
        });
    }

    protected void start(String... args) {
        for (String arg : args) {
            String[] split = arg.split(":");
            if (split.length >= 2) {
                String key = split[0].substring(1);
                List<String> values = new ArrayList<>(Arrays.asList(split));
                values.remove(0);
                String value = String.join(":", values);
            }
        }
        getServer().start(Settings.HOST_ADDRESS.getValue(), Settings.PORT.getValue(), (playerConnection, responseData) -> {
            responseData.clearPlayers();
            responseData.setMaxPlayer(Settings.MAX_PLAYER_COUNT.getValue());
            responseData.setName(Message.MOTD_VERSION.getText(new Placeholder("version", getVersion())));
            responseData.setDescription(Message.MOTD.getText(new Placeholder("version", getVersion())));
        });
        Logger.info("§aStarted server on §8'§6" + Settings.HOST_ADDRESS.getValue() + ":" + Settings.PORT.getValue() + "§8'");
        MinecraftServer.setBrandName(Message.SERVER_BRAND.getText(new Placeholder("version", getVersion())));
    }

    private void registerCommand(@Nonnull Command command) {
        MinecraftServer.getCommandManager().register(command);
        Logger.debug("§aRegistered command §6" + command.getName());
    }

    public void addEventListener(@Nonnull Listener listener) {
        if (!getListeners().contains(listener)) {
            getListeners().add(listener);
            Logger.debug("§aRegistered listener §6" + listener.getClass().getSimpleName());
        } else {
            Logger.error("§cCannot registered listener §4" + listener.getClass().getSimpleName(), "§cAlready registered");
        }
    }

    public void removeEventListener(@Nonnull Listener listener) {
        if (getListeners().contains(listener)) {
            getListeners().remove(listener);
            Logger.debug("§aUnregistered listener §6" + listener.getClass().getSimpleName());
        } else {
            Logger.error("§cCannot unregister listener §4" + listener.getClass().getSimpleName(), "§cWas never registered");
        }
    }
}
