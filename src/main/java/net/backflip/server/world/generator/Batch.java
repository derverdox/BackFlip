package net.backflip.server.world.generator;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.chunk.ChunkUtils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;

public class Batch {

    @Nonnull
    private final HashMap<SimpleBlockPosition, SimpleBlockData> data = new HashMap<>();

    public void setBlock(@Nonnull Position position, @Nonnull Block block) {
        setBlock((int) position.getX(), (int) position.getY(), (int) position.getZ(), block);
    }

    public void setBlock(@Nonnull BlockPosition position, @Nonnull Block block) {
        setBlock(position.getX(), position.getY(), position.getZ(), block);
    }

    public void setBlock(int x, int y, int z, @Nonnull Block block) {
        SimpleBlockPosition position = SimpleBlockPosition.at(x, y, z);
        data.put(position, SimpleBlockData.block(position, block));
    }

    public void setBlockId(@Nonnull Position position, short block) {
        setBlockId((int) position.getX(), (int) position.getY(), (int) position.getZ(), block);
    }

    public void setBlockId(@Nonnull BlockPosition position, short block) {
        setBlockId(position.getX(), position.getY(), position.getZ(), block);
    }

    public void setBlockId(int x, int y, int z, short block) {
        final SimpleBlockPosition at = SimpleBlockPosition.at(x, y, z);
        data.put(at, SimpleBlockData.block(at, block));
    }

    public boolean hasBlockAt(@Nonnull Position position) {
        return hasBlockAt((int) position.getX(), (int) position.getY(), (int) position.getZ());
    }

    public boolean hasBlockAt(@Nonnull BlockPosition position) {
        return hasBlockAt(position.getX(), position.getY(), position.getZ());
    }

    public boolean hasBlockAt(int x, int y, int z) {
        return data.containsKey(SimpleBlockPosition.at(x, y, z));
    }

    public short getBlockIdAt(@Nonnull Position position) {
        return getBlockIdAt((int) position.getX(), (int) position.getY(), (int) position.getZ());
    }

    public short getBlockIdAt(@Nonnull BlockPosition position) {
        return getBlockIdAt(position.getX(), position.getY(), position.getZ());
    }

    public short getBlockIdAt(int x, int y, int z) {
        return data.get(SimpleBlockPosition.at(x, y, z)).blockStateId;
    }

    @Nonnull
    public Block getBlockAt(@Nonnull Position pos) {
        return Block.fromStateId(getBlockIdAt(pos));
    }

    @Nonnull
    public Block getBlockAt(@Nonnull BlockPosition position) {
        return Block.fromStateId(getBlockIdAt(position));
    }

    @Nonnull
    public Block getBlockAt(int x, int y, int z) {
        return Block.fromStateId(getBlockIdAt(x, y, z));
    }

    public void apply(@Nonnull Instance instance, @Nonnull BlockPosition offset) {
        HashSet<SimpleChunkPos> chunks = new HashSet<>();
        for (final SimpleBlockData data : this.data.values()) {
            data.apply(instance, chunks, offset.getX(), offset.getY(), offset.getZ());
        }
        for (final SimpleChunkPos cPos : chunks) {
            cPos.refreshChunk(instance);
        }
    }

    @Nonnull
    public HashMap<SimpleBlockPosition, SimpleBlockData> getData() {
        return data;
    }

    public static class SimpleBlockData {

        public final int x, y, z;
        public final short blockStateId;

        public SimpleBlockData(int x, int y, int z, short blockStateId) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.blockStateId = blockStateId;
        }

        public SimpleBlockData(@Nonnull SimpleBlockPosition position, short blockStateId) {
            this(position.getX(), position.getY(), position.getZ(), blockStateId);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public short getBlockStateId() {
            return blockStateId;
        }

        @Nonnull
        public static SimpleBlockData block(@Nonnull SimpleBlockPosition position, @Nonnull Block block) {
            return new SimpleBlockData(position, block.getBlockId());
        }

        @Nonnull
        public static SimpleBlockData block(@Nonnull SimpleBlockPosition position, short block) {
            return new SimpleBlockData(position, block);
        }

        public void apply(@Nonnull Instance instance, @Nonnull HashSet<SimpleChunkPos> chunks, int xOffset, int yOffset, int zOffset) {
            int rX = x + xOffset;
            int rY = y + yOffset;
            int rZ = z + zOffset;
            Chunk chunk = instance.getChunkAt(rX, rZ);
            if (ChunkUtils.isLoaded(chunk)) {
                synchronized (chunk) {
                    chunk.UNSAFE_setBlock(rX, rY, rZ, blockStateId, (short) 0, null, false);
                    chunks.add(new SimpleChunkPos(chunk.getChunkX(), chunk.getChunkZ()));
                }
            }
        }
    }

    public static class SimpleChunkPos {

        private final int chunkX, chunkZ;

        public SimpleChunkPos(int chunkX, int chunkZ) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }

        public int getChunkX() {
            return chunkX;
        }

        public int getChunkZ() {
            return chunkZ;
        }

        public void refreshChunk(@Nonnull Instance instance) {
            Chunk chunk = instance.getChunk(getChunkX(), getChunkZ());
            if (chunk != null) {
                chunk.sendChunk();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleChunkPos that = (SimpleChunkPos) o;
            if (chunkX != that.chunkX) return false;
            return chunkZ == that.chunkZ;
        }

        @Override
        public int hashCode() {
            int result = chunkX;
            result = 31 * result + chunkZ;
            return result;
        }
    }

    public static class SimpleBlockPosition {

        private final int x, y, z;

        public SimpleBlockPosition(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        @Nonnull
        public static SimpleBlockPosition at(int x, int y, int z) {
            return new SimpleBlockPosition(x, y, z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleBlockPosition that = (SimpleBlockPosition) o;
            if (x != that.x) return false;
            if (y != that.y) return false;
            return z == that.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }
    }
}
