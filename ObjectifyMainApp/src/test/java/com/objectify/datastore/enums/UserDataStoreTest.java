package com.objectify.datastore.enums;

import com.objectify.datastore.adapter.JSONAdapter;
import com.objectify.datastore.adapter.OBJAdapter;
import com.objectify.datastore.adapter.XMLAdapter;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class UserDataStoreTest {

    @Test
    void getDataStore() {
        assertInstanceOf(JSONAdapter.class, UserDataStore.JSON.getDataStore());
        assertInstanceOf(OBJAdapter.class, UserDataStore.OBJ.getDataStore());
        assertInstanceOf(XMLAdapter.class, UserDataStore.XML.getDataStore());
    }

    @Test
    void getName() {
        assertEquals("JSON", UserDataStore.JSON.getName());
        assertEquals("OBJ", UserDataStore.OBJ.getName());
        assertEquals("XML", UserDataStore.XML.getName());
    }

    @Test
    void getDataStoreTypes() {
        String[] expected = {"JSON", "OBJ", "XML"};
        assertArrayEquals(expected, UserDataStore.getDataStoreTypes());
    }

    @Test
    void fromName() {
        assertEquals(UserDataStore.JSON, UserDataStore.fromName("JSON").get());
        assertEquals(UserDataStore.OBJ, UserDataStore.fromName("OBJ").get());
        assertEquals(UserDataStore.XML, UserDataStore.fromName("XML").get());
        
        assertThrows(NoSuchElementException.class, () -> UserDataStore.fromName("BIN").get());
    }
}