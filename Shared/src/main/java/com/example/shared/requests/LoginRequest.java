package com.example.shared.requests;

/**
 * Defines request body for login requests
 */
public class LoginRequest {

    /** Username of current user */
    private String username;
    /** Password of current user */
    private String password;

    /**
     * Creates LoginRequest object with all needed information
     * @param username Username of current user
     * @param password Password of current user
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
