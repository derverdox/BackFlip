package net.backflip.server.api.math;

import net.backflip.server.world.generator.GenerationContext;

import javax.annotation.Nonnull;

public class RandomUtil {

    public static int randomIntBetween(@Nonnull GenerationContext context, int min, int max) {
        int range = max - min;
        return context.getRange().nextInt(range) + min;
    }

    public static float randomFloatBetween(@Nonnull GenerationContext context, float min, float max) {
        float range = max - min;
        return context.getRange().nextFloat() * range + min;
    }

    public static double randomDoubleBetween(@Nonnull GenerationContext context, double min, double max) {
        double range = max - min;
        return context.getRange().nextDouble() * range + min;
    }
}
