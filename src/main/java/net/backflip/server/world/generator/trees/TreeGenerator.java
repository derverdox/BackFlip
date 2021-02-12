package net.backflip.server.world.generator.trees;

import net.backflip.server.world.generator.GenerationContext;

import javax.annotation.Nonnull;

public abstract class TreeGenerator {

    public abstract void generate(@Nonnull GenerationContext context);
}
