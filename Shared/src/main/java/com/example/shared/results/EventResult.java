package com.example.shared.results;

/**
 * Defines response body for event requests
 */
public class EventResult extends Result {

    /** Username of current user */
    private String associatedUsername;
    /** Identifier of event instance */
    private String eventID;
    /** Identifier of person instance */
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
    /** Year that event occurred */
    private int year;

    /**
     * Error constructor
     * @param success determines whether service was successful
     * @param message error message describing error
     */
    public EventResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Creates new EventResult object used to return event with specified ID
     * @param success determines whether service was successful
     * @param associatedUsername Username of current user
     * @param eventID Identifier of event instance
     * @param personID Identifier of person instance
     * @param latitude Latitude of event
     * @param longitude Longitude of event
     * @param country Name of country where event took place
     * @param city Name of city where event took place
     * @param eventType Type of event ie birth, death, marriage etc.
     * @param year Year that event occurred
     */
    public EventResult(boolean success, String associatedUsername, String eventID, String personID, float latitude, float longitude,
                       String country, String city, String eventType, int year) {
        this.success = success;
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
