package com.imjustdoom.minecrash.exception;

import java.io.IOException;

public class ErrorResponseException extends IOException {

    private final String error;

    public ErrorResponseException(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
