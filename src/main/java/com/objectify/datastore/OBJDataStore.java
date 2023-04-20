package com.objectify.datastore;

import java.io.IOException;

public class OBJDataStore implements DataStore{

    @Override
    public void writeData(Object data) throws IOException {

    }

    @Override
    public Object readData() throws IOException {
        return null;
    }

    @Override
    public void deleteData() throws IOException {

    }
    // Kelas ini harus dapat membaca dan menulis data dalam format OBJ dengan menggunakan Java API Serializable.
}
