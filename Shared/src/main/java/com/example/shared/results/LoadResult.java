package com.example.shared.results;

/**
 * Defines response body for load requests
 */
public class LoadResult extends Result {

    /**
     * Creates a new LoadResult object
     * @param success determines whether service was successful
     * @param message message describing success or error
     */
    public LoadResult(boolean success, String message) {
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
