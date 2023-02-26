package com.example.familymapclient.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.shared.models.*;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DataCache {

    public static DataCache instance;

    public static DataCache getInstance() {
        if(instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    private DataCache() {

    }

    private List<Event> lifeStoryEvents;
    private List<Person> family;
    private String authToken;
    private String personID;
    private TreeMap<String, Person> people;
    private TreeMap<String, Event> events;
    private boolean lifeStory = true;
    private boolean familyTree = true;
    private boolean spouseLines = true;
    private boolean fatherSide = true;
    private boolean motherSide = true;
    private boolean maleEvent = true;
    private boolean femaleEvent = true;
    private Event selectedEvent = null;
    private Event selectedEventActivity = null;
    private boolean mainActivity = true;

    public List<Event> orderEvents(TreeMap<String,Event> unorderedEvents, Person person) {
        List<Event> product = new ArrayList<>();
        TreeMap<Integer,Event> orderedEvents = new TreeMap<>();

        for(Map.Entry<String,Event> entry : unorderedEvents.entrySet()) {
            if(entry.getValue().getPersonID().equals(person.getPersonID())) {
                if(orderedEvents.containsKey(entry.getValue().getYear())) {
                    orderedEvents.put(entry.getValue().getYear() + 1, entry.getValue());
                } else {
                    orderedEvents.put(entry.getValue().getYear(), entry.getValue());
                }
            }
        }
        for(Map.Entry<Integer,Event> entry : orderedEvents.entrySet()) {
            product.add(entry.getValue());
        }

        return product;
    }

    public TreeMap<String, Person> getRelations(Person person) {
        TreeMap<String,Person> immediateFamily = new TreeMap<>();
        for(Map.Entry<String,Person> entry : people.entrySet()) {
            if(person.getFatherID() != null) {
                if (entry.getValue().getPersonID().equals(person.getFatherID())) {
                    immediateFamily.put("Father",entry.getValue());
                }
            }
            if(person.getMotherID() != null) {
                if (entry.getValue().getPersonID().equals(person.getMotherID())) {
                    immediateFamily.put("Mother",entry.getValue());
                }
            }
            if(person.getSpouseID() != null) {
                if (entry.getValue().getPersonID().equals(person.getSpouseID())) {
                    immediateFamily.put("Spouse",entry.getValue());
                }
            }
            if(entry.getValue().getMotherID() != null) {
                if (entry.getValue().getMotherID().equals(person.getPersonID())) {
                    immediateFamily.put("Child",entry.getValue());
                }
            }
            if(entry.getValue().getFatherID() != null) {
                if (entry.getValue().getFatherID().equals(person.getPersonID())) {
                    immediateFamily.put("Child",entry.getValue());
                }
            }
        }
        return immediateFamily;
    }

    public List<Event> getLifeStoryEvents() {
        return lifeStoryEvents;
    }

    public void setLifeStoryEvents(List<Event> lifeStoryEvents) {
        this.lifeStoryEvents = lifeStoryEvents;
    }

    public List<Person> getFamily() {
        return family;
    }

    public void setFamily(List<Person> family) {
        this.family = family;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public TreeMap<String, Person> getPeople() {
        return people;
    }

    public void setPeople(TreeMap<String, Person> people) {
        this.people = people;
    }

    public TreeMap<String, Event> getEvents() {
        return events;
    }

    public void setEvents(TreeMap<String, Event> events) {
        this.events = events;
    }

    public boolean isLifeStory() {
        return lifeStory;
    }

    public void setLifeStory(boolean lifeStory) {
        this.lifeStory = lifeStory;
    }

    public boolean isFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(boolean familyTree) {
        this.familyTree = familyTree;
    }

    public boolean isSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(boolean spouseLines) {
        this.spouseLines = spouseLines;
    }

    public boolean isFatherSide() {
        return fatherSide;
    }

    public void setFatherSide(boolean fatherSide) {
        this.fatherSide = fatherSide;
    }

    public boolean isMotherSide() {
        return motherSide;
    }

    public void setMotherSide(boolean motherSide) {
        this.motherSide = motherSide;
    }

    public boolean isMaleEvent() {
        return maleEvent;
    }

    public void setMaleEvent(boolean maleEvent) {
        this.maleEvent = maleEvent;
    }

    public boolean isFemaleEvent() {
        return femaleEvent;
    }

    public void setFemaleEvent(boolean femaleEvent) {
        this.femaleEvent = femaleEvent;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Event getSelectedEventActivity() {
        return selectedEventActivity;
    }

    public void setSelectedEventActivity(Event selectedEventActivity) {
        this.selectedEventActivity = selectedEventActivity;
    }

    public boolean isMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(boolean mainActivity) {
        this.mainActivity = mainActivity;
    }
}
