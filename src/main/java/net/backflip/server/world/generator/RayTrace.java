package net.backflip.server.world.generator;

import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RayTrace {

    @Nonnull
    public static List<Vector> rayTrace(@Nonnull Position position, double step, int steps) {
        return rayTrace(position.toVector(), position.getDirection(), step, steps);
    }

    @Nonnull
    public static List<Vector> rayTrace(@Nonnull Vector startPosition, @Nonnull Vector direction, double step, int steps) {
        direction.normalize().multiply(step);
        List<Vector> positions = new ArrayList<>(steps);
        for (int i = 0; i < steps; i++) {
            positions.add(startPosition.clone().add(direction.clone().multiply(i)));
        }
        return positions;
    }
}
