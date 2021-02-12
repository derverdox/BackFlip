package net.backflip.server.world.generator.trees;

import net.backflip.server.api.math.RandomUtil;
import net.backflip.server.world.generator.AxisBlock;
import net.backflip.server.world.generator.Batch;
import net.backflip.server.world.generator.GenerationContext;
import net.backflip.server.world.generator.RayTrace;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

import javax.annotation.Nonnull;
import java.util.List;

public class Tree {

    @Nonnull
    public static Position placeRay(@Nonnull GenerationContext context, @Nonnull Position startingPos, @Nonnull Block block, int length) {
        List<Vector> positions = RayTrace.rayTrace(startingPos, 0.1, length * 10);
        return collectPosList(context.getBatch(), positions, block);
    }

    @Nonnull
    public static Position placeRay(@Nonnull GenerationContext context, @Nonnull Vector startPosition, @Nonnull Vector direction, @Nonnull Block block, int length) {
        List<Vector> positions = RayTrace.rayTrace(startPosition, direction, 0.1, length * 10);
        return collectPosList(context.getBatch(), positions, block);
    }

    @Nonnull
    private static Position collectPosList(@Nonnull Batch batch, @Nonnull List<Vector> positions, @Nonnull Block block) {
        BlockPosition lastPosition = null;
        for (Vector vector : positions) {
            BlockPosition position = new BlockPosition(vector.getX(), vector.getY(), vector.getZ());
            if (!position.equals(lastPosition)) {
                lastPosition = position;
                batch.setBlock(position, block);
            }
        }
        return positions.get(positions.size() - 1).toPosition();
    }

    public static void placeLeaves(@Nonnull GenerationContext context, @Nonnull BlockPosition position, @Nonnull Block leaf, int dist) {
        if (dist > 0) {
            context.getBatch().setBlock(position, leaf);
            BlockPosition blockPosition = position.clone().add(1, 0, 0);
            if (context.getRange().nextFloat() < 0.6 && !context.getBatch().hasBlockAt(blockPosition)) {
                placeLeaves(context, blockPosition, leaf, dist - 1);
            }
            blockPosition = position.clone().add(-1, 0, 0);
            if (context.getRange().nextFloat() < 0.6 && !context.getBatch().hasBlockAt(blockPosition)) {
                placeLeaves(context, blockPosition, leaf, dist - 1);
            }
            blockPosition = position.clone().add(0, 1, 0);
            if (context.getRange().nextFloat() < 0.6 && !context.getBatch().hasBlockAt(blockPosition)) {
                placeLeaves(context, position, leaf, dist - 1);
            }
            blockPosition = position.clone().add(0, -1, 0);
            if (context.getRange().nextFloat() < 0.6 && !context.getBatch().hasBlockAt(blockPosition)) {
                placeLeaves(context, blockPosition, leaf, dist - 1);
            }
            blockPosition = position.clone().add(0, 0, 1);
            if (context.getRange().nextFloat() < 0.6 && !context.getBatch().hasBlockAt(blockPosition)) {
                placeLeaves(context, blockPosition, leaf, dist - 1);
            }
            blockPosition = position.clone().add(0, 0, -1);
            if (context.getRange().nextFloat() < 0.6 && !context.getBatch().hasBlockAt(blockPosition)) {
                placeLeaves(context, blockPosition, leaf, dist - 1);
            }
        }
    }

    public static void placeCanopyRay(@Nonnull GenerationContext context, @Nonnull Position startPosition, @Nonnull Block logBlock, final Block leafBlock, final int length, final int leafSpread) {
        List<Vector> positions = RayTrace.rayTrace(startPosition, 0.1, length * 10);
        BlockPosition last = startPosition.toBlockPosition();
        for (Vector vec : positions) {
            BlockPosition position = new BlockPosition(vec.getX(), vec.getY(), vec.getZ());
            if (!position.equals(last)) {
                context.getBatch().setBlockId(position, AxisBlock.placeOn(last.toPosition(), position.toPosition(), logBlock));
                last = position;
                if (context.getRange().nextFloat() < 0.6) {
                    placeLeaves(context, position.clone().add(0, 1, 0), leafBlock, leafSpread);
                }
                if (context.getRange().nextFloat() < 0.2) {
                    placeLeaves(context, position.clone().add(0, -1, 0), leafBlock, leafSpread);
                }
                if (context.getRange().nextFloat() < 0.2 && length > 4) {
                    Position pos = vec.toPosition();
                    pos.setDirection(startPosition.getDirection());
                    int difference = RandomUtil.randomIntBetween(context, 30, 70);
                    difference = context.getRange().nextBoolean() ? difference : -difference;
                    int len = length / 2;
                    pos.setYaw(pos.getYaw() + difference);
                    placeCanopyRay(context, pos, logBlock, leafBlock, len, leafSpread);
                    pos.setYaw(pos.getYaw() - difference);
                    placeCanopyRay(context, pos, logBlock, leafBlock, len, leafSpread);
                    break;
                }
            }
        }
    }
}
