package com.example.shared.results;

/**
 * Defines response body for login requests
 */
public class LoginResult extends Result {

    /** Non-empty authorization token string */
    private String authtoken;
    /** Username passed in with request */
    private String username;
    /** Non-empty string containing the Person ID of the user’s Person object */
    private String personID;

    /**
     * Creates a new LoginResult object with all fields
     * @param success determines whether service was successful
     * @param authtoken Non-empty authorization token string
     * @param username  Username passed in with request
     * @param personID Non-empty string containing the Person ID of the user’s Person object
     */
    public LoginResult(boolean success, String authtoken, String username, String personID) {
        this.success = success;
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Error constructor
     * @param success determines whether service was successful
     * @param errorMessage error message describing error
     */
    public LoginResult(boolean success, String errorMessage) {
        this.success = success;
        this.message = errorMessage;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
