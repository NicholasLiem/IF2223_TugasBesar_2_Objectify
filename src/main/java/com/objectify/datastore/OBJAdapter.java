package com.objectify.datastore;

import java.io.IOException;
import java.util.ArrayList;

public class OBJAdapter implements DataStore{

    @Override
    public <T> void writeData(String objFileName, ArrayList<T> data) throws IOException {

    }

    @Override
    public <T> ArrayList<T> readData(String objFileName, Class<T> valueType) throws IOException {
        return null;
    }

    @Override
    public void deleteData() throws IOException {

    }

    @Override
    public <T> void saveData(String objFileName, ArrayList<T> data) throws IOException{

    }
    // Kelas ini harus dapat membaca dan menulis data dalam format OBJ dengan menggunakan Java API Serializable.
}
