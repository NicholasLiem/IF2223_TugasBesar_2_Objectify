package com.objectify.datastore;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    public <T> void writeData(String jsonFileName, ArrayList<T> data) throws IOException {
        try {
            initializeJSONData(jsonFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        if (!data.isEmpty()) {
            for (T item : data) {
                ObjectNode itemNode = JsonNodeFactory.instance.objectNode();
                itemNode.put("type", item.getClass().getSimpleName());
                objectMapper.convertValue(item, JsonNode.class).fields().forEachRemaining(e -> itemNode.set(e.getKey(), e.getValue()));
                arrayNode.add(itemNode);
            }
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(this.jsonPath.toFile(), arrayNode);
    }

    @Override
    public <T> ArrayList<T> readData(String jsonFileName, Class<T> valueType) throws IOException {
        try {
            initializeJSONData(jsonFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.jsonData = Files.readAllBytes(jsonPath);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType);
        return objectMapper.readValue(this.jsonData, type);
    }

    @Override
    public void deleteData() throws IOException {
        Files.deleteIfExists(jsonPath);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode emptyArray = objectMapper.createArrayNode();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(this.jsonPath.toFile(), emptyArray);
    }

    @Override
    public <T> void saveData(String jsonFileName, ArrayList<T> data) throws IOException{
        this.writeData(jsonFileName, data);
        Files.write(jsonPath, jsonData);
    }

    public void initializeJSONData(String jsonFileName) throws IOException {
        this.jsonPath = Paths.get("src", "resources", "JSON", jsonFileName);

        if (!Files.exists(jsonPath)) {
            try {
                Files.createFile(jsonPath);
            } catch (IOException e) {
                throw new IOException("Failed to create JSON file: " + e.getMessage());
            }
        }
    }

}
