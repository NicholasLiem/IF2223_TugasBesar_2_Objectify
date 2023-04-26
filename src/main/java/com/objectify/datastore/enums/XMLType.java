package com.objectify.datastore.enums;
public enum XMLType {
    USERS("Users.xml"),
    TRANSACTIONS("Transactions.xml"),
    PRODUCTS("Products.xml"),
    CATEGORIES("Categories.xml");

    private final String fileName;

    XMLType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
