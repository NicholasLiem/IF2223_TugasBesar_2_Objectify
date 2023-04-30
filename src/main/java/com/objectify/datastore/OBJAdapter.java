package com.objectify.datastore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class OBJAdapter<T> extends DataStore<T> {
    private Path objPath;
    
    public OBJAdapter(String filename) {
        initializeFile(filename);
    }
    
    @Override
    public void write() {
        try (OutputStream outputStream = Files.newOutputStream(objPath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> read() {
        try (InputStream inputStream = Files.newInputStream(objPath)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return Optional.of((T) objectInputStream.readObject());
        } catch (ClassNotFoundException | IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete() {
        try {
            Files.deleteIfExists(objPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getObjPath() {
        return objPath;
    }

    private void initializeFile(String filename) {
        Path resPath = Paths.get("src", "resources", "OBJ");
        try {
            if (!Files.exists(resPath)) {
                Files.createDirectories(resPath);
            }
            objPath = Paths.get(resPath.toString(), filename);
            if (!Files.exists(objPath)) {
                Files.createFile(objPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create OBJ file: " + e.getMessage());
        }
    }
    // Kelas ini harus dapat membaca dan menulis data dalam format OBJ dengan menggunakan Java API Serializable.
}
