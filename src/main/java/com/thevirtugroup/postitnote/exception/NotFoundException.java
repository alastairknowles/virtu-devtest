package com.thevirtugroup.postitnote.exception;

public class NotFoundException extends RuntimeException {

    private final Class<?> type;

    private Object id;

    public NotFoundException(Class<?> type, Object id) {
        this.type = type;
        this.id = id;
    }

}