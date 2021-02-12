package net.backflip.server.commands;

import net.backflip.server.world.entity.EntityLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;

public class SaveAllCommand extends Command {
    public SaveAllCommand() {
        super("save-all");
        setCondition((commandSender, s) -> true);
        setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender player, Arguments arguments) {
        MinecraftServer.getStorageManager().getLocation("entities").saveCachedData();
        MinecraftServer.getInstanceManager().getInstances().forEach(i -> {
            i.saveChunksToStorage(() -> System.out.println("Saved instance "+i));
            i.getChunks().forEach(chunk -> {
                new EntityLoader(i).saveChunkEntities(chunk,null);
            });
        });
    }
}
