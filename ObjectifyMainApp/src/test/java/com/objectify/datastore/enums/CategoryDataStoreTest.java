package com.objectify.datastore.enums;

import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.adapter.OBJAdapter;
import com.objectify.datastore.adapter.XMLAdapter;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDataStoreTest {

    @Test
    void getDataStore() {
        assertInstanceOf(JSONAdapter.class, CategoryDataStore.JSON.getDataStore());
        assertInstanceOf(OBJAdapter.class, CategoryDataStore.OBJ.getDataStore());
        assertInstanceOf(XMLAdapter.class, CategoryDataStore.XML.getDataStore());
    }

    @Test
    void getName() {
        assertEquals("JSON", CategoryDataStore.JSON.getName());
        assertEquals("OBJ", CategoryDataStore.OBJ.getName());
        assertEquals("XML", CategoryDataStore.XML.getName());
    }

    @Test
    void getDataStoreTypes() {
        String[] expected = {"JSON", "OBJ", "XML"};
        assertArrayEquals(expected, CategoryDataStore.getDataStoreTypes());
    }

    @Test
    void fromName() {
        assertEquals(CategoryDataStore.JSON, CategoryDataStore.fromName("JSON").get());
        assertEquals(CategoryDataStore.OBJ, CategoryDataStore.fromName("OBJ").get());
        assertEquals(CategoryDataStore.XML, CategoryDataStore.fromName("XML").get());

        assertThrows(NoSuchElementException.class, () -> CategoryDataStore.fromName("BIN").get());
    }
}