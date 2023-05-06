package com.objectify.datastore;

import com.objectify.exceptions.InvalidArgumentsException;

public interface Command {
    void executeCommand(SystemPointOfSales spos, Object... args) throws InvalidArgumentsException;
}
