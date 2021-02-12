package net.backflip.server.world.generator.trees;

import javax.annotation.Nonnull;

public enum TreeType {
    LARGE(LargeTree.getInstance()),
    MEDIUM(MediumTree.getInstance()),
    PALM(PalmTree.getInstance()),
    ;

    @Nonnull private final TreeGenerator generator;

    TreeType(@Nonnull TreeGenerator generator) {
        this.generator = generator;
    }

    @Nonnull
    public TreeGenerator getGenerator() {
        return generator;
    }
}
