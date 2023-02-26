package com.example.familymapclient.model;

import com.example.shared.models.Event;
import com.example.shared.models.Person;

import java.util.Map;
import java.util.TreeMap;

public class Filter {

    public TreeMap<String, Event> getUserAndSpouse(TreeMap<String, Event> events) {
        TreeMap<String,Event> userAndSpouse = new TreeMap<>();
        DataCache dataCache = DataCache.getInstance();
        Person user = dataCache.getPeople().get(dataCache.getPersonID());
        for(Map.Entry<String, Event> entry : events.entrySet()) {
            Event e = entry.getValue();
            if(e.getPersonID().equals(user.getPersonID())) {
                userAndSpouse.put(e.getEventID(), e);
            }
        }
        if(user.getSpouseID() != null) {
            Person spouse = dataCache.getPeople().get(user.getSpouseID());
            for(Map.Entry<String, Event> entry : events.entrySet()) {
                Event e = entry.getValue();
                if(e.getPersonID().equals(spouse.getPersonID())) {
                    userAndSpouse.put(e.getEventID(), e);
                }
            }
        }
        return userAndSpouse;
    }

    public TreeMap<String, Event> getMaleEvents(TreeMap<String,Event> events) {
        TreeMap<String,Event> maleEvents = new TreeMap<>();
        DataCache dataCache = DataCache.getInstance();
        for(Map.Entry<String, Event> entry : events.entrySet()) {
            Event e = entry.getValue();
            Person p = dataCache.getPeople().get(e.getPersonID());
            if(p.getGender().equals("m")) {
                maleEvents.put(e.getEventID(), e);
            }
        }
        return maleEvents;
    }

    public TreeMap<String,Event> getFemaleEvents(TreeMap<String,Event> events) {
        TreeMap<String,Event> femaleEvents = new TreeMap<>();
        DataCache dataCache = DataCache.getInstance();
        for(Map.Entry<String, Event> entry : events.entrySet()) {
            Event e = entry.getValue();
            Person p = dataCache.getPeople().get(e.getPersonID());
            if(p.getGender().equals("f")) {
                femaleEvents.put(e.getEventID(), e);
            }
        }
        return femaleEvents;
    }

    public TreeMap<String,Event> getFatherSide(TreeMap<String,Event> events) {
        TreeMap<String,Event> fatherSide = new TreeMap<>();
        DataCache dataCache = DataCache.getInstance();
        Person user = dataCache.getPeople().get(dataCache.getPersonID());
        Person father = dataCache.getPeople().get(user.getFatherID());
        TreeMap<String,Person> fatherFamily = new TreeMap<>();
        fatherFamily.put(user.getPersonID(),user);
        fatherFamily.put(father.getPersonID(),father);
        fatherFamily = iterateFamilyTree(father, fatherFamily);

        for(Map.Entry<String, Event> entry : events.entrySet()) {
            Event e = entry.getValue();
            if(fatherFamily.containsKey(e.getPersonID())) {
                fatherSide.put(e.getEventID(),e);
            }
        }
        return fatherSide;
    }

    public TreeMap<String,Event> getMotherSide(TreeMap<String,Event> events) {
        TreeMap<String,Event> motherSide = new TreeMap<>();
        DataCache dataCache = DataCache.getInstance();
        Person user = dataCache.getPeople().get(dataCache.getPersonID());
        Person mother = dataCache.getPeople().get(user.getMotherID());
        TreeMap<String,Person> motherFamily = new TreeMap<>();
        motherFamily.put(user.getPersonID(),user);
        motherFamily.put(mother.getPersonID(),mother);
        motherFamily = iterateFamilyTree(mother, motherFamily);

        for(Map.Entry<String, Event> entry : events.entrySet()) {
            Event e = entry.getValue();
            if(motherFamily.containsKey(e.getPersonID())) {
                motherSide.put(e.getEventID(),e);
            }
        }
        return motherSide;
    }

    public TreeMap<String,Person> iterateFamilyTree(Person curr, TreeMap<String,Person> familyTree) {
        DataCache dataCache = DataCache.getInstance();
        Person father = null;
        Person mother = null;
        if(curr.getFatherID() != null && curr.getMotherID() != null) {
            father = dataCache.getPeople().get(curr.getFatherID());
            mother = dataCache.getPeople().get(curr.getMotherID());
            familyTree.put(father.getPersonID(), father);
            familyTree.put(mother.getPersonID(), mother);
        } else {
            return familyTree;
        }

        familyTree = iterateFamilyTree(father, familyTree);
        familyTree = iterateFamilyTree(mother, familyTree);

        return familyTree;
    }
}
