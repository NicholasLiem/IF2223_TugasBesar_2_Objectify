package com.objectify.exceptions;

public class ItemNotFoundException extends CustomException {
    public ItemNotFoundException() {
        super("Item not found");
    }

    public String getErrorCode() {
        return "Item not found";
    }
}
