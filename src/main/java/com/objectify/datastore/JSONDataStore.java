package com.objectify.datastore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JSONDataStore implements DataStore{

    private final Path jsonPath;
    private byte[] jsonData;

    public JSONDataStore(String jsonFileName) throws FileNotFoundException {
        this.jsonPath = Paths.get("src", "resources", jsonFileName);
        if(!Files.exists(jsonPath)){
            throw new FileNotFoundException("File " + jsonFileName + " does not exists");
        }
    }

    @Override
    public void writeData(Object data) throws IOException {

    }

    @Override
    public ArrayList<?> readData() throws IOException {
        this.jsonData = Files.readAllBytes(jsonPath);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.jsonData, new TypeReference<ArrayList<?>>() {});
    }

    @Override
    public void deleteData() throws IOException {
    }
//    Kelas ini harus dapat membaca dan menulis data dalam format JSON.
}
