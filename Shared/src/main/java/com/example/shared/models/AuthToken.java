package com.example.shared.models;

/**
 * Model class that stores AuthToken data
 */
public class AuthToken {

    /** Unique authorization token associated with session */
    private String authToken;
    /** Username of current user associated with authToken */
    private String username;

    /**
     * Creates AuthToken object passed around to verify user
     * @param authToken Unique authorization token associated with session
     * @param username Username of current user associated with authToken
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Checks whether two objects are equal
     * @param obj Object that this instance is being compared to
     * @return Whether the two are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof AuthToken) {
            AuthToken oAuthToken = (AuthToken) obj;
            return oAuthToken.getAuthToken().equals(getAuthToken()) &&
                    oAuthToken.getUsername().equals(getUsername());
        }
        else {
            return false;
        }
    }
}
