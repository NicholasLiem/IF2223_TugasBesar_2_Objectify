package com.objectify.datastore.enums;
public enum JSONType {
    USERS("Users.json"),
    TRANSACTIONS("Transactions.json"),
    PRODUCTS("Products.json");

    private final String fileName;

    JSONType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
