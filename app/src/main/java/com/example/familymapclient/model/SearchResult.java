package com.example.familymapclient.model;

import com.example.shared.models.Event;
import com.example.shared.models.Person;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class SearchResult {

    private String query;
    private ArrayList<Person> people;
    private ArrayList<Event> events;
    DataCache dataCache;

    public SearchResult(String query) {
        dataCache = DataCache.getInstance();
        this.query = query.toLowerCase(Locale.ROOT);

        people = new ArrayList<>();
        events = new ArrayList<>();

        searchPeople();
        searchEvents();
    }

    private void searchPeople() {
        for (Person person : dataCache.getPeople().values()) {
            if (person.getFirstName().toLowerCase(Locale.ROOT).contains(query)) {
                people.add(person);
            }
            else if (person.getLastName().toLowerCase(Locale.ROOT).contains(query)) {
                people.add(person);
            }
        }
    }

    private void searchEvents() {
        TreeMap<String, Event> filterEvents = new TreeMap<>();

        Filter filter = new Filter();

        TreeMap<String, Event> fatherSide = filter.getFatherSide(dataCache.getEvents());
        TreeMap<String, Event> motherSide = filter.getMotherSide(dataCache.getEvents());

        TreeMap<String, Event> maleFatherSide = filter.getMaleEvents(fatherSide);
        TreeMap<String, Event> femaleFatherSide = filter.getFemaleEvents(fatherSide);
        TreeMap<String, Event> maleMotherSide = filter.getMaleEvents(motherSide);
        TreeMap<String, Event> femaleMotherSide = filter.getFemaleEvents(motherSide);

        if(dataCache.isMaleEvent() && dataCache.isFatherSide()) {
            for(Map.Entry<String,Event> entry : maleFatherSide.entrySet()) {
                filterEvents.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isFemaleEvent() && dataCache.isFatherSide()) {
            for(Map.Entry<String,Event> entry : femaleFatherSide.entrySet()) {
                filterEvents.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isMaleEvent() && dataCache.isMotherSide()) {
            for(Map.Entry<String,Event> entry : maleMotherSide.entrySet()) {
                filterEvents.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isFemaleEvent() && dataCache.isMotherSide()) {
            for(Map.Entry<String,Event> entry : femaleMotherSide.entrySet()) {
                filterEvents.put(entry.getKey(),entry.getValue());
            }
        }
        for (Event event : filterEvents.values()) {
            searchEvent(event);
        }
    }

    private void searchEvent(Event event) {
        if (event.getEventType().toLowerCase(Locale.ROOT).contains(query)) {
            events.add(event);
            return;
        }
        if (event.getCity().toLowerCase(Locale.ROOT).contains(query)) {
            events.add(event);
            return;
        }
        if (event.getCountry().toLowerCase(Locale.ROOT).contains(query)) {
            events.add(event);
            return;
        }
        if ((Integer.toString(event.getYear()).contains(query))) {
            events.add(event);
            return;
        }

        Person eventOwner = dataCache.getPeople().get(event.getPersonID());
        String fullName = eventOwner.getFirstName() + " " + eventOwner.getLastName();
        if (fullName.toLowerCase(Locale.ROOT).contains(query)) {
            events.add(event);
            return;
        }
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}