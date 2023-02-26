package com.example.shared.models;

/**
 * Model class that stores Person data
 */
public class Person {

    /** Unique identifier of person */
    private String personID;
    /** Username of current user */
    private String associatedUsername;
    /** First name of person */
    private String firstName;
    /** Last name of person */
    private String lastName;
    /** Gender of person, either 'f' or 'g' */
    private String gender;
    /** Unique identifier of person's mother */
    private String motherID;
    /** Unique identifier of person's father */
    private String fatherID;
    /** Unique identifier of person's spouse */
    private String spouseID;

    /**
     * Creates Person object with all required fields
     * @param personID Unique identifier of person
     * @param associatedUser Username of current user
     * @param firstName First name of person
     * @param lastName Last name of person
     * @param gender Gender of person, either 'f' or 'g'
     * @param motherID Unique identifier of person's mother
     * @param fatherID Unique identifier of person's father
     * @param spouseID Unique identifier of person's spouse
     */
    public Person(String personID, String associatedUser, String firstName, String lastName, String gender,
                  String motherID, String fatherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.motherID = motherID;
        this.fatherID = fatherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUser() {
        return associatedUsername;
    }

    public void setAssociatedUser(String associatedUser) {
        this.associatedUsername = associatedUser;
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

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
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
        if(obj instanceof Person) {
            Person oPerson = (Person) obj;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUser().equals(getAssociatedUser()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());
        } else {
            return false;
        }
    }
}
