package com.objectify.datastore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JSONAdapter implements DataStore{

    private static JSONAdapter jsonAdapterInstance = null;

    private Path jsonPath;
    private byte[] jsonData;

    private JSONAdapter(){

    }

    public static synchronized JSONAdapter getInstance(){
        if (jsonAdapterInstance == null){
            jsonAdapterInstance = new JSONAdapter();
        }
        return jsonAdapterInstance;
    }

    @Override
    public void writeData(Object data) throws IOException {

    }

    @Override
    public <T> ArrayList<T> readData(String jsonFileName, Class<T> valueType) throws IOException {
        try {
            this.initializeJsonData(jsonFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType);
        return objectMapper.readValue(this.jsonData, type);
    }

    @Override
    public void deleteData() throws IOException {
    }

    public void initializeJsonData(String jsonFileName) throws Exception{
        this.jsonPath = Paths.get("src", "resources", "JSON", jsonFileName);
        if(!Files.exists(jsonPath)){
            throw new FileNotFoundException("File " + jsonFileName + " does not exists");
        }
        this.jsonData = Files.readAllBytes(jsonPath);
    }
}
