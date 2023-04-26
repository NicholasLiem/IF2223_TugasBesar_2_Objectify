package com.objectify.datastore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OBJAdapter implements DataStore {
    
    private final Path resourcesPath = Paths.get("src", "resources", "OBJ");
    {
        if (!Files.exists(this.resourcesPath)) {
            try {
                Files.createDirectories(resourcesPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> void writeData(String filename, ArrayList<T> data) throws IOException {
        Path objPath = Paths.get(resourcesPath.toString(), filename);
        OutputStream outputStream = Files.newOutputStream(objPath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    @Override
    public <T> ArrayList<T> readData(String filename, Class<T> valueType) throws IOException {
        Path objPath = Paths.get(resourcesPath.toString(), filename);
        InputStream inputStream = Files.newInputStream(objPath);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (ArrayList<T>) objectInputStream.readObject(); // unchecked cast, or not (?)
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteData() throws IOException {
        // I don't know what to do
    }

    @Override
    public <T> void saveData(String filename, ArrayList<T> data) throws IOException {
        writeData(filename, data);
    }
    // Kelas ini harus dapat membaca dan menulis data dalam format OBJ dengan menggunakan Java API Serializable.
}
