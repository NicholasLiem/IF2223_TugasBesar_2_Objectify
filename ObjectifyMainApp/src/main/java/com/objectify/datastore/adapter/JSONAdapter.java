package com.objectify.datastore.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.objectify.datastore.interfaces.DataStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class JSONAdapter<T> implements DataStore<T> {

    private Path jsonPath;
    private final String filename;
    private final ObjectMapper mapper;
    private final Class<T> cls;

    public JSONAdapter(String filename, Class<T> cls) {
        if (!filename.endsWith(".json")) {
            filename += ".json";
        }
        jsonPath = Paths.get("ObjectifyMainApp","src", "resources", "JSON", filename);
        this.filename = filename;
        this.cls = cls;
        initializeFile(filename);
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
        } catch (MismatchedInputException e) {
            System.out.println("Either file empty or fail to parse");
            return Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void delete() {
        try {
            Files.deleteIfExists(jsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Path getJsonPath() {
        return jsonPath;
    }

    private void initializeFile(String filename) {
        if (!filename.endsWith(".json")) {
            filename += ".json";
        }
        Path resPath = Paths.get("datastore", "resources", "JSON");
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
