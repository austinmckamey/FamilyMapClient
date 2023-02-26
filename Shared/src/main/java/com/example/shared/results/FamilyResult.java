package com.example.shared.results;

import com.example.shared.models.*;

import java.util.ArrayList;

public class FamilyResult extends Result {

    /** Array with all family members of current user */
    private ArrayList<Person> data;

    /**
     * Error constructor
     * @param success determines whether service was successful
     * @param message error message describing error
     */
    public FamilyResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Success constructor
     * @param success determines whether service was successful
     * @param data array of all family members of current user
     */
    public FamilyResult(boolean success, ArrayList<Person> data) {
        this.success = success;
        this.data = data;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
