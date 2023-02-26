package com.example.shared.results;

import com.example.shared.models.*;


import java.util.ArrayList;

/**
 * defines result body for all events of all family members of current user
 */
public class AllEventsResult extends Result {

    /** Array with all events of all family members of current user */
    private ArrayList<Event> data;

    /**
     * Error constructor
     * @param success determines whether service was successful
     * @param message error message describing error
     */
    public AllEventsResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Success constructor
     * @param success determines whether service was successful
     * @param data array of events of all family members of current user
     */
    public AllEventsResult(boolean success, ArrayList<Event> data) {
        this.success = success;
        this.data = data;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
