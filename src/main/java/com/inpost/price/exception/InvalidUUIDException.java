package com.inpost.price.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidUUIDException extends IllegalArgumentException{
    public InvalidUUIDException(String sentId) {
        super(String.format("Sent id: [%s] is not a valid UUID", sentId));
    }
}
