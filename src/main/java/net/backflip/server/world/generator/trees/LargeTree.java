package net.backflip.server.world.generator.trees;

import net.backflip.server.api.math.RandomUtil;
import net.backflip.server.world.generator.GenerationContext;
import net.backflip.server.world.generator.VectorUtil;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

import javax.annotation.Nonnull;

public class LargeTree extends TreeGenerator {

    @Nonnull public static LargeTree instance = new LargeTree();

    @Nonnull
    public static LargeTree getInstance() {
        return instance;
    }

    protected LargeTree() {
    }

    @Override
    public void generate(@Nonnull GenerationContext context) {
        Position focalPoint = context.getOrigin().clone().add(0.5, 5, 0.5);
        Position center = context.getOrigin().clone().subtract(0, 3, 0);
        int count = RandomUtil.randomIntBetween(context, 3, 6);
        double fraction = 1.0 / count;
        double degree = 360 * fraction;
        double distance = RandomUtil.randomDoubleBetween(context, 3, 6);
        for (int i = 0; i < count; i++) {
            Position temp = center.clone();
            temp.setYaw((float) (RandomUtil.randomDoubleBetween(context, 0, degree) + degree * i));
            Position positionA = temp.getDirection().multiply(distance).toPosition().add(temp);
            Vector vec = VectorUtil.vectorBetweenTwoPoints(positionA, focalPoint);
            Tree.placeRay(context, positionA.toVector(), vec, Block.OAK_LOG, (int) vec.length());
        }

        focalPoint.setPitch(RandomUtil.randomIntBetween(context, -85, -75));
        focalPoint.setYaw(RandomUtil.randomIntBetween(context, -180, 180));
        Position position = Tree.placeRay(context, focalPoint, Block.OAK_LOG, RandomUtil.randomIntBetween(context, 5, 12));
        count = RandomUtil.randomIntBetween(context, 7, 10);
        fraction = 1.0 / count;
        degree = 360 * fraction;
        for (int i = 0; i < count; i++) {
            position.setPitch(RandomUtil.randomIntBetween(context, 10, 20));
            position.setYaw((float) (RandomUtil.randomDoubleBetween(context, 0, degree) + degree * i));
            Tree.placeCanopyRay(context, position, Block.OAK_WOOD, Block.OAK_LEAVES, RandomUtil.randomIntBetween(context, 15, 25), 5);
        }
    }
}
