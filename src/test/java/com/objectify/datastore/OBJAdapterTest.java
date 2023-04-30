package com.objectify.datastore;

import com.objectify.models.items.Product;
import com.objectify.models.items.StorageManager;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class OBJAdapterTest {

    private final StorageManager storageManager = new StorageManager();
    {
        storageManager.addProduct(new Product());
        storageManager.addProduct(new Product());
        storageManager.addProduct(new Product());
    }
    private final OBJAdapter<StorageManager> dataStore = new OBJAdapter<>("test.obj");

    @Test
    @Order(1)
    void write() {
        dataStore.setData(storageManager);
        dataStore.write();

        try {
            String contents = new String(Files.readAllBytes(dataStore.getObjPath()));
            assertTrue(contents.contains("StorageManager"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void read() throws InterruptedException {
        Thread.sleep(100);
        StorageManager writtenUserManager = dataStore.read().orElse(new StorageManager());
        assertEquals(storageManager.getProducts().size(), writtenUserManager.getProducts().size());
        for (int i = 0; i < storageManager.getProducts().size(); i++) {
            String expected = storageManager.getProducts().get(i).toString();
            String actual = writtenUserManager.getProducts().get(i).toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void delete() {
    }
}