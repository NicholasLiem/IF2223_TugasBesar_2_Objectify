package com.objectify.datastore;

import java.io.IOException;
import java.util.ArrayList;

public class XMLAdapter implements DataStore{
    @Override
    public <T> void writeData(String xmlFileName, ArrayList<T> data) throws IOException {

    }

    @Override
    public <T> ArrayList<T> readData(String xmlFileName, Class<T> valueType) throws IOException {
        return null;
    }

    @Override
    public void deleteData() throws IOException {

    }

    @Override
    public <T> void saveData(String xmlFileName, ArrayList<T> data) throws IOException{

    }
}
