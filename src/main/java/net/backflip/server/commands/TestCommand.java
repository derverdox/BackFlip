package net.backflip.server.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.type.animal.EntityCow;

public class TestCommand extends Command {
    public TestCommand() {
        super("tester");

        setDefaultExecutor((commandSender, arguments) -> {
            if(!(commandSender instanceof Player))
                return;
            Player player = (Player) commandSender;
            EntityCow entityCow = new EntityCow(player.getPosition());
            entityCow.setInstance(player.getInstance());
            entityCow.addViewer(player);
            entityCow.spawn();
        });
    }
}
