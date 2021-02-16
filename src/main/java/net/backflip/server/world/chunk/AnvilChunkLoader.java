package net.backflip.server.world.chunk;

import net.backflip.server.api.logger.Logger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.data.Data;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.DynamicChunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockAlternative;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.registry.Registries;
import net.minestom.server.storage.StorageLocation;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.chunk.ChunkCallback;
import net.minestom.server.world.biomes.Biome;
import org.jglrxavpok.hephaistos.mca.AnvilException;
import org.jglrxavpok.hephaistos.mca.ChunkColumn;
import org.jglrxavpok.hephaistos.mca.CoordinatesKt;
import org.jglrxavpok.hephaistos.mca.RegionFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//TODO: Chunk File Kapazität? -> Nich dass wir mit irgend nem NBT Bullshit vollgemüllt werden ^^

public class AnvilChunkLoader implements IChunkLoader {

    @Nonnull private final Biome voidBiome;
    @Nonnull private final StorageLocation regionFolder;
    @Nonnull private final ConcurrentHashMap<String, RegionFile> alreadyLoaded = new ConcurrentHashMap<>();
    @Nonnull private final Instance instance;

    public AnvilChunkLoader(StorageLocation regionFolder, Instance instance) {
        this.regionFolder = regionFolder;
        this.instance = instance;
        Biome defaultBiome;
        defaultBiome = MinecraftServer.getBiomeManager().getByName(NamespaceID.from("minecraft:the_void"));
        if (defaultBiome == null) {
            defaultBiome = Biome.PLAINS;
        }
        this.voidBiome = defaultBiome;
    }

    @Nonnull
    public Biome getVoidBiome() {
        return voidBiome;
    }

    @Nonnull
    public StorageLocation getRegionFolder() {
        return regionFolder;
    }

    @Nonnull
    public ConcurrentHashMap<String, RegionFile> getAlreadyLoaded() {
        return alreadyLoaded;
    }

    @Nonnull
    public Instance getInstance() {
        return instance;
    }

    @Override
    public boolean loadChunk(@Nonnull Instance instance, int chunkX, int chunkZ, @Nonnull ChunkCallback callback) {
        Logger.debug("Attempt loading at " + chunkX + ", " + chunkZ);
        try {
            Chunk chunk = loadMCA(instance, chunkX, chunkZ, callback);
            return chunk != null;
        } catch (IOException | AnvilException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void saveChunk(@Nonnull Chunk chunk, @Nullable Runnable callback) {
        int chunkX = chunk.getChunkX();
        int chunkZ = chunk.getChunkZ();
        RegionFile mcaFile;
        synchronized (alreadyLoaded) {
            mcaFile = getMCAFile(chunkX, chunkZ);
            if (mcaFile == null) {
                int regionX = CoordinatesKt.chunkToRegion(chunkX);
                int regionZ = CoordinatesKt.chunkToRegion(chunkZ);
                String n = RegionFile.Companion.createFileName(regionX, regionZ);
                File regionFile = new File(regionFolder.getLocation(), n);
                try {
                    if (!regionFile.exists()) {
                        if (!regionFile.getParentFile().exists()) {
                            regionFile.getParentFile().mkdirs();
                        }
                        regionFile.createNewFile();
                    }
                    mcaFile = new RegionFile(new RandomAccessFile(regionFile, "rw"), regionX, regionZ);
                    alreadyLoaded.put(n, mcaFile);
                } catch (AnvilException | IOException e) {
                    Logger.error("Failed to save chunk " + chunkX + ", " + chunkZ);
                    e.printStackTrace();
                    return;
                }
            }
        }
        int[] biomes = new int[Chunk.BIOME_COUNT];
        for (int i = 0; i < biomes.length; i++) {
            Biome biome = chunk.getBiomes()[i];
            if (biome == null) {
                biome = voidBiome;
            }
            biomes[i] = biome.getId();
        }
        ChunkColumn column;
        try {
            column = mcaFile.getOrCreateChunk(chunkX, chunkZ);
        } catch (AnvilException | IOException e) {
            Logger.error("Failed to save chunk " + chunkX + ", " + chunkZ);
            e.printStackTrace();
            return;
        }
        save(chunk, column);
        try {
            Logger.debug("Attempt saving at " + chunk.getChunkX() + ", " + chunk.getChunkZ());
            mcaFile.writeColumn(column);
        } catch (IOException e) {
            Logger.error("Failed to save chunk " + chunkX + ", " + chunkZ);
            e.printStackTrace();
            return;
        }
        if (callback != null) {
            callback.run();
        }
        mcaFile.forget(column);
    }

    @Nullable
    private Chunk loadMCA(@Nonnull Instance instance, int chunkX, int chunkZ, @Nonnull ChunkCallback callback) throws IOException, AnvilException {
        RegionFile mcaFile = getMCAFile(chunkX, chunkZ);
        if (mcaFile != null) {
            ChunkColumn fileChunk = mcaFile.getChunk(chunkX, chunkZ);
            if (fileChunk != null) {
                Biome[] biomes = new Biome[Chunk.BIOME_COUNT];
                if (fileChunk.getGenerationStatus().compareTo(ChunkColumn.GenerationStatus.Biomes) > 0) {
                    int[] fileChunkBiomes = fileChunk.getBiomes();
                    for (int i = 0; i < fileChunkBiomes.length; i++) {
                        int id = fileChunkBiomes[i];
                        Biome biome = MinecraftServer.getBiomeManager().getById(id);
                        if (biome == null) {
                            biome = voidBiome;
                        }
                        biomes[i] = biome;
                    }
                } else {
                    Arrays.fill(biomes, voidBiome);
                }
                Chunk loadedChunk = new DynamicChunk(biomes, chunkX, chunkZ);
                ChunkBatch batch = instance.createChunkBatch(loadedChunk);
                loadBlocks(instance, chunkX, chunkZ, batch, fileChunk);
                batch.unsafeFlush(callback);

                return loadedChunk;
            }
        }
        return null;
    }

    @Nullable
    private RegionFile getMCAFile(int chunkX, int chunkZ) {
        int regionX = CoordinatesKt.chunkToRegion(chunkX);
        int regionZ = CoordinatesKt.chunkToRegion(chunkZ);
        return alreadyLoaded.computeIfAbsent(RegionFile.Companion.createFileName(regionX, regionZ), n -> {
            try {
                File regionFile = new File(regionFolder.getLocation(), n);
                if (!regionFile.exists()) {
                    return null;
                }
                return new RegionFile(new RandomAccessFile(regionFile, "rw"), regionX, regionZ);
            } catch (IOException | AnvilException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /*
    private void loadTileEntities(Chunk loadedChunk, int chunkX, int chunkZ, Instance instance, ChunkColumn fileChunk) {
        BlockPosition pos = new BlockPosition(0, 0, 0);
        for (NBTCompound te : fileChunk.getTileEntities()) {
            // String tileEntityID = te.getString("id");
            int x = te.getInt("x") + chunkX * 16;
            int y = te.getInt("y");
            int z = te.getInt("z") + chunkZ * 16;
            CustomBlock block = loadedChunk.getCustomBlock(x, y, z);
            if (block != null && block instanceof VanillaBlock) {
                pos.setX(x);
                pos.setY(y);
                pos.setZ(z);
                Data data = loadedChunk.getBlockData(ChunkUtils.getBlockIndex(x, y, z));
                data = ((VanillaBlock) block).readBlockEntity(te, instance, pos, data);
                loadedChunk.setBlockData(x, y, z, data);
            }
        }
    }
     */

    private void loadBlocks(@Nonnull Instance instance, int chunkX, int chunkZ, @Nonnull ChunkBatch batch, @Nonnull ChunkColumn fileChunk) {
        for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
            for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                for (int y = 0; y < Chunk.CHUNK_SIZE_Y; y++) {
                    try {
                        org.jglrxavpok.hephaistos.mca.BlockState blockState = fileChunk.getBlockState(x, y, z);
                        Block rBlock = Registries.getBlock(blockState.getName());
                        short customBlockId = 0;
                        Data data = null;
                        CustomBlock customBlock = MinecraftServer.getBlockManager().getCustomBlock(rBlock.getBlockId());
                        if (customBlock != null) {
                            customBlockId = rBlock.getBlockId();

                            data = customBlock.createData(instance, new BlockPosition(x + chunkX * 16, y, z + chunkZ * 16), null);
                        }
                        if (!blockState.getProperties().isEmpty()) {
                            List<String> propertiesArray = new ArrayList<>();
                            blockState.getProperties().forEach((key, value2) -> propertiesArray.add(key + "=" + value2.replace("\"", "")));
                            Collections.sort(propertiesArray);
                            short block = rBlock.withProperties(propertiesArray.toArray(new String[0]));
                            if (customBlock != null) {
                                batch.setSeparateBlocks(x, y, z, block, customBlockId, data);
                            } else {
                                batch.setBlockStateId(x, y, z, block);
                            }
                        } else {
                            if (customBlock != null) {
                                batch.setSeparateBlocks(x, y, z, rBlock.getBlockId(), customBlockId, data);
                            } else {
                                batch.setBlock(x, y, z, rBlock);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /*
    private void saveTileEntities(Chunk chunk, ChunkColumn fileChunk) {
        NBTList<NBTCompound> tileEntities = new NBTList<>(NBTTypes.TAG_Compound);
        BlockPosition position = new BlockPosition(0, 0, 0);

        for (var index : chunk.getBlockEntities()) {
            int x = ChunkUtils.blockIndexToChunkPositionX(index);
            int y = ChunkUtils.blockIndexToChunkPositionY(index);
            int z = ChunkUtils.blockIndexToChunkPositionZ(index);
            position.setX(x);
            position.setY(y);
            position.setZ(z);
            CustomBlock customBlock = chunk.getCustomBlock(x, y, z);
            if (customBlock instanceof VanillaBlock) {
                NBTCompound nbt = new NBTCompound();
                nbt.setInt("x", x);
                nbt.setInt("y", y);
                nbt.setInt("z", z);
                nbt.setByte("keepPacked", (byte) 0);
                Block block = Block.fromStateId(customBlock.getDefaultBlockStateId());
                Data data = chunk.getBlockData(ChunkUtils.getBlockIndex(x, y, z));
                customBlock.writeBlockEntity(position, data, nbt);
                if (block.hasBlockEntity()) {
                    nbt.setString("id", block.getBlockEntityName().toString());
                    tileEntities.add(nbt);
                } else {
                    LOGGER.debug("Tried to save block entity for a block which is not a block entity? Block is {} at {},{},{}", customBlock, x, y, z);
                }
            }
        }
        fileChunk.setTileEntities(tileEntities);
    }
     */

    private void save(@Nonnull Chunk chunk, @Nonnull ChunkColumn chunkColumn) {
        chunkColumn.setGenerationStatus(ChunkColumn.GenerationStatus.Full);
        // TODO: other elements to save
        //saveTileEntities(chunk, chunkColumn);
        for (int x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
            for (int z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                for (int y = 0; y < Chunk.CHUNK_SIZE_Y; y++) {
                    short id = chunk.getBlockStateId(x, y, z);
                    // CustomBlock customBlock = chunk.getCustomBlock(x, y, z);
                    Block block = Block.fromStateId(id);
                    BlockAlternative alt = block.getAlternative(id);
                    Map<String, String> properties = alt.createPropertiesMap();
                    org.jglrxavpok.hephaistos.mca.BlockState state = new org.jglrxavpok.hephaistos.mca.BlockState(block.getName(), properties);
                    chunkColumn.setBlockState(x, y, z, state);
                    // https://wiki.vg/Chunk_Format#Biomes
                    int index = ((y >> 2) & 63) << 4 | ((z >> 2) & 3) << 2 | ((x >> 2) & 3);
                    Biome biome = chunk.getBiomes()[index];
                    chunkColumn.setBiome(x, 0, z, biome.getId());
                }
            }
        }
    }

    @Override
    public boolean supportsParallelLoading() {
        return true;
    }

    @Override
    public boolean supportsParallelSaving() {
        return true;
    }
}
