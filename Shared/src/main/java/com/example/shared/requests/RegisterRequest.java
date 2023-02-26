package com.example.shared.requests;

/**
 * Defines request body for register requests
 */
public class RegisterRequest {

    /** Username of current user */
    private String username;
    /** Password of current user */
    private String password;
    /** Email of current user */
    private String email;
    /** First name of current user */
    private String firstName;
    /** Last name of current user */
    private String lastName;
    /** Gender of current user, either 'f' or 'g' */
    private String gender;

    /**
     * Creates RegisterRequest object with all needed information
     * @param username Username of current user
     * @param password Password of current user
     * @param email Email of current user
     * @param firstName First name of current user
     * @param lastName Last name of current user
     * @param gender Gender of current user, either 'f' or 'g'
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
