package net.backflip.server.world.generator;

import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

import javax.annotation.Nonnull;
import java.util.Random;

public class GenerationContext {

    @Nonnull private final Random range = new Random();
    @Nonnull private final Batch batch = new Batch();
    @Nonnull private final Position origin = new Position();

    @Nonnull
    public Random getRange() {
        return range;
    }

    @Nonnull
    public Batch getBatch() {
        return batch;
    }

    @Nonnull
    public Position getOrigin() {
        return origin;
    }

    public void complete(@Nonnull Instance instance, @Nonnull BlockPosition offset) {
        batch.apply(instance, offset);
    }
}
