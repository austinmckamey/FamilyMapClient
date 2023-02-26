package com.example.shared.models;

/**
 * Model class that stores Event data
 */
public class Event {

    /** Unique identifier of event */
    private String eventID;
    /** Username of current user */
    private String associatedUsername;
    /** Unique identifier of person associated with event */
    private String personID;
    /** Latitude of event */
    private float latitude;
    /** Longitude of event */
    private float longitude;
    /** Name of country where event took place */
    private String country;
    /** Name of city where event took place */
    private String city;
    /** Type of event ie birth, death, marriage etc. */
    private String eventType;
    /** Year that the event occurred */
    private int year;

    /**
     * Creates Event object with all required fields
     * @param eventID Unique identifier of event
     * @param username Username of current user
     * @param personID Unique identifier of person associated with event
     * @param latitude Latitude of event
     * @param longitude Longitude of event
     * @param country Name of country where event took place
     * @param city Name of city where event took place
     * @param eventType Type of event ie birth, death, marriage etc.
     * @param year Year that the event occurred
     */
    public Event(String eventID, String username, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Checks whether two objects are equal
     * @param obj Object that this instance is being compared to
     * @return Whether the two are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Event) {
            Event oEvent = (Event) obj;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }
}
