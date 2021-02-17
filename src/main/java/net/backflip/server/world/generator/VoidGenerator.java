package net.backflip.server.world.generator;

import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidGenerator implements ChunkGenerator {
    @Override
    public void generateChunkData(@NotNull ChunkBatch chunkBatch, int i, int i1) {
    }

    @Override
    public void fillBiomes(@NotNull Biome[] biomes, int i, int i1) {
    }

    @Nullable
    @Override
    public List<ChunkPopulator> getPopulators() {
        return null;
    }
}
