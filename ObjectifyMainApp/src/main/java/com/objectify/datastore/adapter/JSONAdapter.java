package com.objectify.datastore.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.objectify.datastore.enums.DataStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class JSONAdapter<T> implements DataStore<T> {

    private Path jsonPath;
    private final ObjectMapper mapper;
    private final Class<T> cls;

    public JSONAdapter(String filename, Class<T> cls) {
        initializeFile(filename);
        this.cls = cls;
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void write(T data) {
        assert(data.getClass().equals(cls));
        try {
            mapper.writeValue(jsonPath.toFile(), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> read() {
        try {
            T result = mapper.readValue(jsonPath.toFile(), cls);
            return Optional.of(result);
        } catch (MismatchedInputException exception) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete() {
        try {
            Files.deleteIfExists(jsonPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Path getJsonPath() {
        return jsonPath;
    }

    private void initializeFile(String filename) {
        Path resPath = Paths.get("src", "resources", "JSON");
        try {
            if (!Files.exists(resPath)) {
                Files.createDirectories(resPath);
            }
            jsonPath = Paths.get(resPath.toString(), filename);
            if (!Files.exists(jsonPath)) {
                Files.createFile(jsonPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create JSON file: " + e.getMessage());
        }
    }
}
