package com.objectify.datastore.enums;

import com.objectify.datastore.SystemPointOfSales;
import com.objectify.exceptions.InvalidArgumentsException;

public interface Command {
    void executeCommand(SystemPointOfSales spos, Object... args) throws InvalidArgumentsException;
}
