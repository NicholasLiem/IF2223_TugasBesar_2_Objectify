package com.objectify.datastore;

import java.io.IOException;
import java.util.ArrayList;

public interface DataStore {


    <T> void writeData(String jsonFileName, ArrayList<T> data) throws IOException;

    <T> ArrayList<T> readData(String jsonFileName, Class<T> valueType) throws IOException;

    void deleteData() throws IOException;

    <T> void saveData(String jsonFileName, ArrayList<T> data) throws IOException;

}
