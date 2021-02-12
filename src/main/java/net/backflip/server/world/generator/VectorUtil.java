package net.backflip.server.world.generator;

import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

import javax.annotation.Nonnull;

public class VectorUtil {

    @Nonnull
    public static Vector vectorBetweenTwoPoints(@Nonnull Position pointA, @Nonnull Position pointB) {
        return pointB.clone().subtract(pointA.getX(), pointA.getY(), pointA.getZ()).toVector();
    }
}
