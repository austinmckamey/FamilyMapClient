package com.example.shared.results;

/**
 * Defines response body for clear requests
 */
public class ClearResult extends Result {

    /**
     * Creates a new ClearResult object
     */
    public ClearResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
