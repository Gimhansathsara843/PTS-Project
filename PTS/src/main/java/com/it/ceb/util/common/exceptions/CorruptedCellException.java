package com.it.ceb.util.common.exceptions;

public class CorruptedCellException extends Exception {
    public CorruptedCellException() {
        super("Cell is corrupted or unreadable format");
    }
}
