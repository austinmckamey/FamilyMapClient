package com.example.familymapclient;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import com.example.familymapclient.model.DataCache;
import com.example.familymapclient.model.Filter;
import com.example.familymapclient.model.SearchResult;
import com.example.shared.models.Event;
import com.example.shared.models.Person;

import java.util.List;
import java.util.TreeMap;

public class DataCacheTest {

    DataCache dataCache;

    @BeforeEach
    public void setUp() {
        dataCache = DataCache.getInstance();

        dataCache.setPersonID("user");

        Person user = new Person("user","testUser","User","Test",
                "m","userMother","userFather","userSpouse");
        Person father = new Person("userFather","testUser","Dad","Test",
                "m",null,null,"userMother");
        Person mother = new Person("userMother","testUser","Mom","Test",
                "f",null,null,"userFather");
        Person spouse = new Person("userSpouse","testUser","Honey","Test",
                "f",null,null,"user");
        Person child = new Person("child","testUser","Kid","Test",
                "m",null,"user",null);

        TreeMap <String, Person> people = new TreeMap<>();
        people.put(user.getPersonID(), user);
        people.put(father.getPersonID(), father);
        people.put(mother.getPersonID(), mother);
        people.put(spouse.getPersonID(), spouse);
        people.put(child.getPersonID(), child);

        dataCache.setPeople(people);

        Event userBirth = new Event("userBirth","testUser","user",24,
                25,"France","Paris","Birth",2000);
        Event userMarriage = new Event("userMarriage","testUser","user",24,
                25,"France","Paris","Marriage",2020);
        Event userDeath = new Event("userDeath","testUser","user",24,
                25,"France","Paris","Death",2080);

        Event fatherBirth = new Event("fatherBirth","testUser","userFather",24,
                25,"France","Paris","Birth",1975);
        Event fatherMarriage = new Event("fatherMarriage","testUser","userFather",24,
                25,"France","Paris","Marriage",1995);
        Event fatherSushi = new Event("fatherSushi","testUser","userFather",24,
                25,"France","Paris","Sushi",1995);
        Event fatherDeath = new Event("fatherDeath","testUser","userFather",24,
                25,"France","Paris","Death",2065);

        Event motherBirth = new Event("motherBirth","testUser","userMother",24,
                25,"France","Paris","Birth",1975);
        Event motherMarriage = new Event("motherMarriage","testUser","userMother",24,
                25,"France","Paris","Marriage",1995);
        Event motherDeath = new Event("motherDeath","testUser","userMother",24,
                25,"France","Nice","Death",2065);

        Event spouseBirth = new Event("spouseBirth","testUser","userSpouse",24,
                25,"France","Paris","Birth",2001);
        Event spouseMarriage = new Event("spouseMarriage","testUser","userSpouse",24,
                25,"France","Paris","Marriage",2020);
        Event spouseDeath = new Event("spouseDeath","testUser","userSpouse",24,
                25,"France","Paris","Death",2081);

        Event childBirth = new Event("childBirth","testUser","child",24,
                25,"France","Paris","Birth",2025);
        Event childMarriage = new Event("childMarriage","testUser","child",24,
                25,"France","Paris","Marriage",2045);
        Event childDeath = new Event("childDeath","testUser","child",24,
                25,"France","Paris","Death",2105);

        TreeMap<String, Event> events = new TreeMap<>();
        events.put(userBirth.getEventID(),userBirth);
        events.put(userMarriage.getEventID(),userMarriage);
        events.put(userDeath.getEventID(),userDeath);

        events.put(fatherBirth.getEventID(),fatherBirth);
        events.put(fatherMarriage.getEventID(),fatherMarriage);
        events.put(fatherSushi.getEventID(),fatherSushi);
        events.put(fatherDeath.getEventID(),fatherDeath);

        events.put(motherBirth.getEventID(),motherBirth);
        events.put(motherMarriage.getEventID(),motherMarriage);
        events.put(motherDeath.getEventID(),motherDeath);

        events.put(spouseBirth.getEventID(),spouseBirth);
        events.put(spouseMarriage.getEventID(),spouseMarriage);
        events.put(spouseDeath.getEventID(),spouseDeath);

        events.put(childBirth.getEventID(),childBirth);
        events.put(childMarriage.getEventID(),childMarriage);
        events.put(childDeath.getEventID(),childDeath);

        dataCache.setEvents(events);

        //load people and events into dataCache
    }

    @Test
    public void familyRelationsPass() {
        Person user = dataCache.getPeople().get("user");
        TreeMap<String, Person> immediateFamily = dataCache.getRelations(user);

        assertEquals("Dad", immediateFamily.get("Father").getFirstName());
        assertEquals("Mom", immediateFamily.get("Mother").getFirstName());
        assertEquals("Honey", immediateFamily.get("Spouse").getFirstName());
        assertEquals("Kid", immediateFamily.get("Child").getFirstName());
    }

    @Test
    public void familyRelationsFail() {
        Person child = dataCache.getPeople().get("child");
        TreeMap<String, Person> immediateFamily = dataCache.getRelations(child);

        assertNull(immediateFamily.get("Child"));
    }

    @Test
    public void filterEventsPass() {
        Filter filter = new Filter();

        TreeMap<String, Event> fatherSide = filter.getFatherSide(dataCache.getEvents());
        TreeMap<String, Event> motherSide = filter.getMotherSide(dataCache.getEvents());

        TreeMap<String, Event> maleFatherSide = filter.getMaleEvents(fatherSide);
        TreeMap<String, Event> femaleMotherSide = filter.getFemaleEvents(motherSide);

        assertTrue(maleFatherSide.containsKey("fatherBirth"));
        assertTrue(femaleMotherSide.containsKey("motherBirth"));
    }

    @Test
    public void filterEventsPass2() {
        Filter filter = new Filter();

        TreeMap<String, Event> fatherSide = filter.getFatherSide(dataCache.getEvents());
        TreeMap<String, Event> motherSide = filter.getMotherSide(dataCache.getEvents());

        TreeMap<String, Event> maleFatherSide = filter.getMaleEvents(fatherSide);
        TreeMap<String, Event> femaleMotherSide = filter.getFemaleEvents(motherSide);

        assertFalse(maleFatherSide.containsKey("motherBirth"));
        assertFalse(femaleMotherSide.containsKey("fatherBirth"));
    }

    @Test
    public void orderEventsPass() {
        Person user = dataCache.getPeople().get("user");
        TreeMap<String,Event> events = dataCache.getEvents();
        List<Event> lifeStory = dataCache.orderEvents(events,user);

        assertEquals(2000,lifeStory.get(0).getYear());
        assertEquals(2020,lifeStory.get(1).getYear());
        assertEquals(2080,lifeStory.get(2).getYear());
    }

    @Test
    public void orderEventsSameYear() {
        Person father = dataCache.getPeople().get("userFather");
        TreeMap<String,Event> events = dataCache.getEvents();
        List<Event> lifeStory = dataCache.orderEvents(events,father);

        assertEquals(1975,lifeStory.get(0).getYear());
        assertEquals(1995,lifeStory.get(1).getYear());
        assertEquals(1995,lifeStory.get(2).getYear());
        assertEquals(2065,lifeStory.get(3).getYear());
    }

    @Test
    public void searchPass() {
        TreeMap<String,Person> allPeople = dataCache.getPeople();
        TreeMap<String,Event> allEvents = dataCache.getEvents();

        SearchResult searchResult = new SearchResult("Test");
        List<Person> people = searchResult.getPeople();

        assertTrue(people.contains(allPeople.get("user")));

        searchResult = new SearchResult("ice");
        List<Event> events = searchResult.getEvents();

        assertTrue(events.contains(allEvents.get("motherDeath")));
    }

    @Test
    public void searchNotFound() {
        TreeMap<String,Person> allPeople = dataCache.getPeople();
        TreeMap<String,Event> allEvents = dataCache.getEvents();

        SearchResult searchResult = new SearchResult("Germany");
        List<Person> people = searchResult.getPeople();
        List<Event> events = searchResult.getEvents();

        assertEquals(0,people.size());
        assertEquals(0,events.size());
    }
}
