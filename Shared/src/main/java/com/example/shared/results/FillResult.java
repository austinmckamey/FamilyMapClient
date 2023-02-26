package com.example.shared.results;

/**
 * Defines response body for fill requests
 */
public class FillResult extends Result {

    /**
     * Creates a new FillResult object
     * @param success determines whether service was successful
     * @param message message describing success or error
     */
    public FillResult(boolean success, String message) {
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
