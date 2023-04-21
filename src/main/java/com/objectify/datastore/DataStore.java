package com.objectify.datastore;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;

public interface DataStore {

    void writeData(Object data) throws IOException;

    <T> ArrayList<T> readData(String jsonFileName, Class<T> valueType) throws IOException;

    void deleteData() throws IOException;

    void saveAllData() throws IOException;

}
