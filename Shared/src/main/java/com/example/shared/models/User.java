package com.example.shared.models;

/**
 * Model class that stores User data
 */
public class User {

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
    /** Unique identifier of person associated with current user */
    private String personID;

    /**
     * Creates User object with all required fields
     * @param username Username of current user
     * @param password Password of current user
     * @param email Email of current user
     * @param firstName First name of current user
     * @param lastName Last name of current user
     * @param gender Gender of current user, either 'f' or 'g'
     * @param personID Unique identifier of person associated with current user
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Checks whether two objects are equal
     * @param obj Object that this instance is being compared to
     * @return Whether the two are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof User) {
            User oUser = (User) obj;
            return oUser.getUsername().equals(getUsername()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());
        } else {
            return false;
        }
    }
}
