package com.example.shared.results;

public class Result {

    /** Message to be sent, whether true or false */
    public String message;
    /** Variable that is set whether the action was successful or not */
    public boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
