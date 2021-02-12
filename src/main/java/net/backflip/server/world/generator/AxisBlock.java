package net.backflip.server.world.generator;

import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;

import javax.annotation.Nonnull;

public class AxisBlock {

    public static short placeOn(@Nonnull Position surface, @Nonnull Position newBlock, @Nonnull Block block) {
        Vector vector = VectorUtil.vectorBetweenTwoPoints(newBlock, surface);
        Position pos = new Position();
        pos.setDirection(vector);
        String axis;
        if (pos.getPitch() > 45 || pos.getPitch() < -45) {
            axis = "y";
        } else {
            if (pos.getYaw() > 135 || pos.getYaw() < -135 || (pos.getYaw() < 45 && pos.getYaw() > -45)) {
                axis = "z";
            } else {
                axis = "x";
            }
        }
        return block.withProperties("axis=" + axis);
    }
}
