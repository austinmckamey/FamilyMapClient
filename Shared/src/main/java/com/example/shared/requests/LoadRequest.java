package com.example.shared.requests;

import com.example.shared.models.*;

import java.util.ArrayList;

/**
 * Defines request body for load requests
 */
public class LoadRequest {

    /** Array of users to be added */
    private ArrayList<User> users;
    /** Array of persons to be added */
    private ArrayList<Person> persons;
    /** Array of events to be added */
    private ArrayList<Event> events;

    /**
     * Creates LoadRequest object with all needed information
     * @param users Array of users to be added
     * @param persons Array of persons to be added
     * @param events Array of events to be added
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
