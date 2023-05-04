package com.objectify.datastore.enums;

import com.objectify.datastore.JSONAdapter;
import com.objectify.datastore.OBJAdapter;
import com.objectify.datastore.XMLAdapter;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsDataStoreTest {

    @Test
    void getDataStore() {
        assertInstanceOf(JSONAdapter.class, TransactionsDataStore.JSON.getDataStore());
        assertInstanceOf(OBJAdapter.class, TransactionsDataStore.OBJ.getDataStore());
        assertInstanceOf(XMLAdapter.class, TransactionsDataStore.XML.getDataStore());
    }

    @Test
    void getName() {
        assertEquals("JSON", TransactionsDataStore.JSON.getName());
        assertEquals("OBJ", TransactionsDataStore.OBJ.getName());
        assertEquals("XML", TransactionsDataStore.XML.getName());
    }

    @Test
    void getDataStoreTypes() {
        String[] expected = {"JSON", "OBJ", "XML"};
        assertArrayEquals(expected, TransactionsDataStore.getDataStoreTypes());
    }

    @Test
    void fromName() {
        assertEquals(TransactionsDataStore.JSON, TransactionsDataStore.fromName("JSON").get());
        assertEquals(TransactionsDataStore.OBJ, TransactionsDataStore.fromName("OBJ").get());
        assertEquals(TransactionsDataStore.XML, TransactionsDataStore.fromName("XML").get());

        assertThrows(NoSuchElementException.class, () -> TransactionsDataStore.fromName("BIN").get());
    }
}