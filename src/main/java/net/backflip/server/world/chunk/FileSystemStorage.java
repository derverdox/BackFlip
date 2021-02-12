package net.backflip.server.world.chunk;

import net.minestom.server.storage.StorageOptions;
import net.minestom.server.storage.StorageSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemStorage implements StorageSystem {

    private Path openedPath;

    @Override
    public void open(String folderPath, StorageOptions options) {
        this.openedPath = Path.of(folderPath);
    }

    @Override
    public void close() {}

    @Override
    public boolean exists(String folderPath) {
        return Files.exists(Path.of(folderPath));
    }

    @Override
    public byte[] get(String key) {
        try {
            Path path = getPath(key);
            if(!Files.exists(path)) {
                return null;
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Path getPath(String key) {
        Path path = Path.of(this.openedPath+"/"+key+".dat");
        if(!path.toFile().getParentFile().exists())
            path.toFile().getParentFile().mkdirs();
        return path;
    }

    @Override
    public void set(String key, byte[] data) {
        try {
            Files.write(getPath(key), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        try {
            Files.delete(getPath(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
