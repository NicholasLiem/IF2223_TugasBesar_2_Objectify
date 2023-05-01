package com.objectify.datastore;

import com.objectify.models.entities.Customer;
import com.objectify.models.entities.Member;
import com.objectify.models.entities.UserManager;
import com.objectify.models.entities.VIP;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JSONAdapterTest {

    private final UserManager userManager = new UserManager();
    {
        userManager.addUser(new Customer());
        userManager.addUser(new Member());
        userManager.addUser(new VIP());
    }
    private final JSONAdapter<UserManager> dataStore = new JSONAdapter<>("test.json", UserManager.class);

    @Test
    @Order(1)
    void write() {
        dataStore.write(userManager);

        try {
            String contents = new String(Files.readAllBytes(dataStore.getJsonPath()));
            assertTrue(contents.contains("Customer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void read() throws InterruptedException {
        Thread.sleep(100);
        UserManager writtenUserManager = dataStore.read().orElse(new UserManager());
        assertEquals(userManager.getListOfUsers().size(), writtenUserManager.getListOfUsers().size());
        for (int i = 0; i < userManager.getListOfUsers().size(); i++) {
            String expected = userManager.getListOfUsers().get(i).toString();
            String actual = writtenUserManager.getListOfUsers().get(i).toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    void delete() {
    }
}