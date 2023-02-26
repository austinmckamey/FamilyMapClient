package com.example.familymapclient.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familymapclient.R;
import com.example.familymapclient.model.DataCache;
import com.example.familymapclient.model.Filter;
import com.example.shared.models.Event;
import com.example.shared.models.Person;
import com.google.android.gms.maps.model.Marker;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PersonActivity extends AppCompatActivity {

    private Person person;
    private List<String> relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataCache dataCache = DataCache.getInstance();

        String selected = getIntent().getStringExtra("personOfInterest");
        person = dataCache.getPeople().get(selected);

        setContentView(R.layout.activity_person);

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        List<Person> family = new ArrayList<>();
        List<Event> lifeStory = new ArrayList<>();
        relation = new ArrayList<>();

        TreeMap<String, Event> events = new TreeMap<>();
        TreeMap<String,Person> people = dataCache.getPeople();

        TextView firstName = findViewById(R.id.firstNamePerson);
        firstName.setText(person.getFirstName());
        TextView lastName = findViewById(R.id.lastNamePerson);
        lastName.setText(person.getLastName());
        TextView gender = findViewById(R.id.genderPerson);
        if(person.getGender().equals("m")) {
            gender.setText("Male");
        } else {
            gender.setText("Female");
        }

        TreeMap<String, Event> allEvents = dataCache.getEvents();

        Filter filter = new Filter();

        TreeMap<String, Event> userAndSpouse = filter.getUserAndSpouse(allEvents);
        TreeMap<String, Event> fatherSide = filter.getFatherSide(allEvents);
        TreeMap<String, Event> motherSide = filter.getMotherSide(allEvents);

        TreeMap<String, Event> maleUser = filter.getMaleEvents(userAndSpouse);
        TreeMap<String, Event> femaleUser = filter.getFemaleEvents(userAndSpouse);
        TreeMap<String, Event> maleFatherSide = filter.getMaleEvents(fatherSide);
        TreeMap<String, Event> femaleFatherSide = filter.getFemaleEvents(fatherSide);
        TreeMap<String, Event> maleMotherSide = filter.getMaleEvents(motherSide);
        TreeMap<String, Event> femaleMotherSide = filter.getFemaleEvents(motherSide);

        if(dataCache.isMaleEvent()) {
            for(Map.Entry<String,Event> entry : maleUser.entrySet()) {
                events.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isFemaleEvent()) {
            for(Map.Entry<String,Event> entry : femaleUser.entrySet()) {
                events.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isMaleEvent() && dataCache.isFatherSide()) {
            for(Map.Entry<String,Event> entry : maleFatherSide.entrySet()) {
                events.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isFemaleEvent() && dataCache.isFatherSide()) {
            for(Map.Entry<String,Event> entry : femaleFatherSide.entrySet()) {
                events.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isMaleEvent() && dataCache.isMotherSide()) {
            for(Map.Entry<String,Event> entry : maleMotherSide.entrySet()) {
                events.put(entry.getKey(),entry.getValue());
            }
        }
        if(dataCache.isFemaleEvent() && dataCache.isMotherSide()) {
            for(Map.Entry<String,Event> entry : femaleMotherSide.entrySet()) {
                events.put(entry.getKey(),entry.getValue());
            }
        }

        lifeStory = dataCache.orderEvents(events,person);

        for(Map.Entry<String,Person> entry : people.entrySet()) {
            if(person.getFatherID() != null) {
                if (entry.getValue().getPersonID().equals(person.getFatherID())) {
                    family.add(entry.getValue());
                    relation.add("Father");
                }
            }
            if(person.getMotherID() != null) {
                if (entry.getValue().getPersonID().equals(person.getMotherID())) {
                    family.add(entry.getValue());
                    relation.add("Mother");
                }
            }
            if(person.getSpouseID() != null) {
                if (entry.getValue().getPersonID().equals(person.getSpouseID())) {
                    family.add(entry.getValue());
                    relation.add("Spouse");
                }
            }
            if(entry.getValue().getMotherID() != null) {
                if (entry.getValue().getMotherID().equals(person.getPersonID())) {
                    family.add(entry.getValue());
                    relation.add("Child");
                }
            }
            if(entry.getValue().getFatherID() != null) {
                if (entry.getValue().getFatherID().equals(person.getPersonID())) {
                    family.add(entry.getValue());
                    relation.add("Child");
                }
            }
        }

        expandableListView.setAdapter(new ExpandableListAdapter(family, lifeStory));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final List<Person> family;
        private final List<Event> lifeStory;

        ExpandableListAdapter(List<Person> family, List<Event> lifeStory) {
            this.family = family;
            this.lifeStory = lifeStory;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case 0:
                    return lifeStory.size();
                case 1:
                    return family.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position");
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case 0:
                    return getString(R.string.lifeStory);
                case 1:
                    return getString(R.string.family);
                default:
                    throw new IllegalArgumentException("Unrecognized group position");
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case 0:
                    return lifeStory.get(childPosition);
                case 1:
                    return family.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position");
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.expandable_groups, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case 0:
                    titleView.setText(R.string.lifeStory);
                    break;
                case 1:
                    titleView.setText(R.string.family);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position");
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;
            switch (groupPosition) {
                case 0:
                    itemView = getLayoutInflater().inflate(R.layout.expandable_group_description, parent, false);
                    initializeLifeStoryView(itemView, childPosition);
                    break;
                case 1:
                    itemView = getLayoutInflater().inflate(R.layout.expandable_group_description, parent, false);
                    initializeFamilyView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position");
            }
            return itemView;
        }

        private void initializeLifeStoryView(View lifeStoryView, final int childPosition) {
            ImageView icon = lifeStoryView.findViewById(R.id.personGenderIcon);

            Drawable genderIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_map_marker).
                    colorRes(R.color.black).sizeDp(30);
            icon.setImageDrawable(genderIcon);

            Event e = lifeStory.get(childPosition);

            TextView itemTitle = lifeStoryView.findViewById(R.id.listItemTitle);
            String text = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")";
            itemTitle.setText(text);

            TextView itemDescription = lifeStoryView.findViewById(R.id.listItemDescription);
            text = person.getFirstName() + " " + person.getLastName();
            itemDescription.setText(text);

            lifeStoryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataCache dataCache = DataCache.getInstance();
                    dataCache.setSelectedEventActivity(e);
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    PersonActivity.this.startActivity(intent);
                }
            });
        }

        private void initializeFamilyView(View familyView, final int childPosition) {
            Person p = family.get(childPosition);

            ImageView icon = familyView.findViewById(R.id.personGenderIcon);

            Drawable genderIcon;
            if (p.getGender().equals("m")) {
                genderIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_male).
                        colorRes(R.color.male_icon).sizeDp(30);
            } else {
                genderIcon = new IconDrawable(PersonActivity.this, FontAwesomeIcons.fa_female).
                        colorRes(R.color.female_icon).sizeDp(30);
            }
            icon.setImageDrawable(genderIcon);

            TextView itemTitle = familyView.findViewById(R.id.listItemTitle);
            String text = p.getFirstName() + " " + p.getLastName();
            itemTitle.setText(text);

            TextView itemDescription = familyView.findViewById(R.id.listItemDescription);
            text = relation.get(childPosition);
            itemDescription.setText(text);

            familyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Person clicked = family.get(childPosition);
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    intent.putExtra("personOfInterest", clicked.getPersonID());
                    PersonActivity.this.startActivity(intent);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent= new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}