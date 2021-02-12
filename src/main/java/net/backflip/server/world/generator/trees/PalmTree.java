package net.backflip.server.world.generator.trees;

import net.backflip.server.api.math.RandomUtil;
import net.backflip.server.world.generator.GenerationContext;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PalmTree extends TreeGenerator {

    @Nonnull
    public static PalmTree instance = new PalmTree();

    @Nonnull
    public static PalmTree getInstance() {
        return instance;
    }

    protected PalmTree() {
    }

    @Override
    public void generate(@NotNull GenerationContext context) {
        Position position = context.getOrigin();
        position.setPitch(-RandomUtil.randomIntBetween(context, 45, 80));
        position.setYaw(RandomUtil.randomIntBetween(context, -180, 180));
        position = Tree.placeRay(context, position, Block.OAK_LOG, RandomUtil.randomIntBetween(context, 6, 15));
        for (int i = 0; i < 6; i++) {
            position.setPitch(RandomUtil.randomIntBetween(context, -40, 60));
            position.setYaw(RandomUtil.randomIntBetween(context, 0, 60) + i * 60);
            Tree.placeRay(context, position, Block.OAK_LEAVES, 7);
        }
    }
}
