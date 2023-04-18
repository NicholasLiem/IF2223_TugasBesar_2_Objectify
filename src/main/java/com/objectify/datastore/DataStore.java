package com.objectify.datastore;

import java.io.IOException;

public interface DataStore {

    void writeData(Object data) throws IOException;

    Object readData() throws IOException;

    void deleteData() throws IOException;

}
