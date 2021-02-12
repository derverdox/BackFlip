package net.backflip.server.world;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.biomes.Biome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class WorldGenerator implements ChunkGenerator {

    @Override
    public void generateChunkData(@Nonnull ChunkBatch chunkBatch, int i, int i1) {
        for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
            for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                for (byte y = 0; y < 40; y++) {
                    chunkBatch.setBlock(x, y, z, Block.STONE);
                }
            }
        }
    }

    @Override
    public void fillBiomes(@Nonnull Biome[] biomes, int i, int i1) {
        Arrays.fill(biomes, Biome.PLAINS);
    }

    @Nullable
    @Override
    public List<ChunkPopulator> getPopulators() {
        return null;
    }
}
