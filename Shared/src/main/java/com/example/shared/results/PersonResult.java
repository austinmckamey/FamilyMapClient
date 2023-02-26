package com.example.shared.results;

/**
 * Defines response body for person requests
 */
public class PersonResult extends Result {

    /** Username of current user */
    private String associatedUsername;
    /** Identifier of person instance */
    private String personID;
    /** First name of person */
    private String firstName;
    /** Last name of person */
    private String lastName;
    /** Gender of person, either 'f' or 'g' */
    private String gender;
    /** Identifier of person's father */
    private String fatherID;
    /** Identifier of person's mother */
    private String motherID;
    /** Identifier of person's spouse */
    private String spouseID;

    /**
     * Error constructor
     * @param success determines whether service was successful
     * @param message error message describing error
     */
    public PersonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Creates new PersonResult object to return person with specified ID
     * @param success determines whether service was successful
     * @param associatedUsername Username of current user
     * @param personID Identifier of person instance
     * @param firstName First name of person
     * @param lastName Last name of person
     * @param gender Gender of person, either 'f' or 'g'
     * @param fatherID Identifier of person's father
     * @param motherID Identifier of person's mother
     * @param spouseID Identifier of person's spouse
     */
    public PersonResult(boolean success, String associatedUsername, String personID, String firstName,
                        String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.success = success;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
