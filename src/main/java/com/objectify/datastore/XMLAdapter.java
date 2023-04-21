package com.objectify.datastore;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;

public class XMLAdapter implements DataStore{
    @Override
    public void writeData(Object data) throws IOException {

    }

    @Override
    public <T> ArrayList<T> readData(String xmlFileName, Class<T> valueType) throws IOException {
        return null;
    }

    @Override
    public void deleteData() throws IOException {

    }
    //  Kelas ini harus dapat membaca dan menulis data dalam format XML.
}
