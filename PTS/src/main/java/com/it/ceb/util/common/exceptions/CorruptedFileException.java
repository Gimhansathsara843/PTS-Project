package com.it.ceb.util.common.exceptions;

public class CorruptedFileException extends Exception {
    public CorruptedFileException() {
        super("File is corrupted or unreadable");
    }
}
