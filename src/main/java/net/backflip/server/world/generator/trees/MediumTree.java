package net.backflip.server.world.generator.trees;

import net.backflip.server.api.math.RandomUtil;
import net.backflip.server.world.generator.GenerationContext;
import net.backflip.server.world.generator.Interpolation;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class MediumTree extends TreeGenerator {

    @Nonnull
    public static MediumTree instance = new MediumTree();

    @Nonnull
    public static MediumTree getInstance() {
        return instance;
    }

    protected MediumTree() {
    }

    @Override
    public void generate(@NotNull GenerationContext context) {
        double _00 = RandomUtil.randomDoubleBetween(context, -1.2, 1);
        double _01 = RandomUtil.randomDoubleBetween(context, 0, 1);
        double _02 = RandomUtil.randomDoubleBetween(context, -1.2, 1);
        double _10 = RandomUtil.randomDoubleBetween(context, 0, 0.8);
        double _11 = RandomUtil.randomDoubleBetween(context, 1, 2);
        double _12 = RandomUtil.randomDoubleBetween(context, 0, 1);
        double _20 = RandomUtil.randomDoubleBetween(context, -1.2, 1);
        double _21 = RandomUtil.randomDoubleBetween(context, 0, 1.5);
        double _22 = RandomUtil.randomDoubleBetween(context, -1.2, 1);
        double[] array = new double[]{_00, _01, _02, _10, _11, _12, _20, _21, _22};
        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                final double v = Interpolation.bilinearInterpolation(array, 3, 6, x, z);
                placeRoots(context, v, x, 0, z, Block.OAK_WOOD, Block.OAK_SLAB);
            }
        }
        Position position = new Position(2, (int) _11, 2);
        position.setPitch(-90);
        position.setYaw(RandomUtil.randomFloatBetween(context, -180, 180));
        position = Tree.placeRay(context, position, Block.OAK_LOG, RandomUtil.randomIntBetween(context, 3, 6));
        int count = RandomUtil.randomIntBetween(context, 4, 7);
        double fraction = 1.0 / count;
        double degPer = 360 * fraction;
        for (int i = 0; i < count; i++) {
            position.setPitch(RandomUtil.randomIntBetween(context, -15, 0));
            position.setYaw((float) (RandomUtil.randomDoubleBetween(context, 0, degPer) + degPer * i));
            Tree.placeCanopyRay(context, position, Block.OAK_WOOD, Block.OAK_LEAVES, RandomUtil.randomIntBetween(context, 3, 5), 5);
        }
    }

    public static void placeRoots(@Nonnull GenerationContext context, double v, int x, int y, int z, @Nonnull Block log, @Nonnull Block slab) {
        if (v >= 0) {
            int i = (int) v;
            double dec = v - i;
            int count = i;
            boolean needsSlab = false;
            if (dec > 0.75) {
                count += 1;
            } else if (dec > 0.25 && context.getRange().nextFloat() < 0.5) {
                needsSlab = true;
            }
            for (int j = 0; j < count; j++) {
                context.getBatch().setBlock(x, y + j, z, log);
            }
            if (needsSlab) {
                context.getBatch().setBlock(x, y + count, z, slab);
            }
        }
    }
}
