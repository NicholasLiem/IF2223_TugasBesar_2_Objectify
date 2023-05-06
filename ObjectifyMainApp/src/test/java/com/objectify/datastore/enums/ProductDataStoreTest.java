package com.objectify.datastore.enums;

import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.adapter.OBJAdapter;
import com.objectify.datastore.adapter.XMLAdapter;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductDataStoreTest {

    @Test
    void getDataStore() {
        assertInstanceOf(JSONAdapter.class, ProductDataStore.JSON.getDataStore());
        assertInstanceOf(OBJAdapter.class, ProductDataStore.OBJ.getDataStore());
        assertInstanceOf(XMLAdapter.class, ProductDataStore.XML.getDataStore());
    }

    @Test
    void getName() {
        assertEquals("JSON", ProductDataStore.JSON.getName());
        assertEquals("OBJ", ProductDataStore.OBJ.getName());
        assertEquals("XML", ProductDataStore.XML.getName());
    }

    @Test
    void getDataStoreTypes() {
        String[] expected = {"JSON", "OBJ", "XML"};
        assertArrayEquals(expected, ProductDataStore.getDataStoreTypes());
    }

    @Test
    void fromName() {
        assertEquals(ProductDataStore.JSON, ProductDataStore.fromName("JSON").get());
        assertEquals(ProductDataStore.OBJ, ProductDataStore.fromName("OBJ").get());
        assertEquals(ProductDataStore.XML, ProductDataStore.fromName("XML").get());

        assertThrows(NoSuchElementException.class, () -> ProductDataStore.fromName("BIN").get());
    }
}