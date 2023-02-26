package com.example.familymapclient.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.familymapclient.model.Filter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import com.example.familymapclient.R;
import com.example.familymapclient.model.DataCache;
import com.example.shared.models.Event;
import com.example.shared.models.Person;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private static final int MAGENTA = -65281;
    public static final int BLUE = -16776961;
    public static final int BLACK = -16777216;
    private GoogleMap map;
    private ImageView icon;
    private View view;
    private HashMap<Marker, String> markerID;
    private ArrayList<Polyline> polylines;
    private TreeMap<String, Float> colors;
    private int colorIndex = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        view = layoutInflater.inflate(R.layout.fragment_map, container, false);

        DataCache dataCache = DataCache.getInstance();
        if(dataCache.isMainActivity()) {
            setHasOptionsMenu(true);
        }
        icon = view.findViewById(R.id.eventGenderIcon);
        markerID = new HashMap<>();
        polylines = new ArrayList<>();
        colors = new TreeMap<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DataCache dataCache = DataCache.getInstance();
        map = googleMap;
        map.setOnMapLoadedCallback(this);
        GoogleMap.OnMarkerClickListener listener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String eventID = markerID.get(marker);

                dataCache.setSelectedEvent(dataCache.getEvents().get(eventID));

                updateEventInfo(eventID);
                updatePolylines(eventID);

                LinearLayout eventInfo = view.findViewById(R.id.mapTextView);
                eventInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PersonActivity.class);
                        intent.putExtra("personOfInterest", dataCache.getSelectedEvent().getPersonID());
                        startActivity(intent);
                    }
                });
                return false;
            }
        };
        map.setOnMarkerClickListener(listener);

        updateMarkers();

        Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).
                colorRes(R.color.android).sizeDp(50);
        icon.setImageDrawable(genderIcon);

        if(!dataCache.isMainActivity()) {
            centerOverSelectedEvent();
            updateMarkers();
            updateEventInfo(dataCache.getSelectedEventActivity().getEventID());
            updatePolylines(dataCache.getSelectedEventActivity().getEventID());
            LinearLayout eventInfo = view.findViewById(R.id.mapTextView);
            eventInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("personOfInterest", dataCache.getSelectedEvent().getPersonID());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);

        menu.findItem(R.id.searchMenuItem).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                        .colorRes(R.color.white)
                        .actionBarSize());

        menu.findItem(R.id.settingsMenuItem).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
                        .colorRes(R.color.white)
                        .actionBarSize());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.searchMenuItem:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;

            case R.id.settingsMenuItem:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onMapLoaded() {
        // You probably don't need this callback. It occurs after onMapReady and I have seen
        // cases where you get an error when adding markers or otherwise interacting with the map in
        // onMapReady(...) because the map isn't really all the way ready. If you see that, just
        // move all code where you interact with the map (everything after
        // map.setOnMapLoadedCallback(...) above) to here.
    }

    @Override
    public void onResume() {
        super.onResume();
        if(map != null) {
            DataCache dataCache = DataCache.getInstance();
            updateMarkers();
            updateEventInfo(dataCache.getSelectedEvent().getEventID());
            updatePolylines(dataCache.getSelectedEvent().getEventID());
        }
    }

    private float getColor(String eventType) {
        if(!colors.containsKey(eventType.toLowerCase(Locale.ROOT))) {
            float color = newColor();
            colors.put(eventType.toLowerCase(Locale.ROOT), color);
            return color;
        } else {
            return colors.get(eventType.toLowerCase(Locale.ROOT));
        }
    }

    private float newColor() {
        if(colorIndex == 7) {
            colorIndex = 0;
        }
        switch (colorIndex) {
            case 0:
                ++colorIndex;
                return 180;
            case 1:
                ++colorIndex;
                return 240;
            case 2:
                ++colorIndex;
                return 270;
            case 3:
                ++colorIndex;
                return 60;
            case 4:
                ++colorIndex;
                return 120;
            case 5:
                ++colorIndex;
                return 30;
            case 6:
                ++colorIndex;
                return 300;
            default:
                return 0;
        }
    }

    private void updateEventInfo(String eventID) {
        DataCache dataCache = DataCache.getInstance();
        TextView text = view.findViewById(R.id.eventText);
        if(!markerID.containsValue(eventID)) {
            String eventText = "Click on a marker to see Event details";

            text.setText(eventText);

            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).
                    colorRes(R.color.android).sizeDp(50);
            icon.setImageDrawable(genderIcon);
        } else {
            Event event = dataCache.getEvents().get(eventID);
            Person person = dataCache.getPeople().get(event.getPersonID());

            String eventText = person.getFirstName() + " " + person.getLastName() + '\n' +
                    event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() +
                    " (" + event.getYear() + ")";

            text.setText(eventText);

            Drawable genderIcon;
            if (person.getGender().equals("m")) {
                genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).
                        colorRes(R.color.male_icon).sizeDp(50);
            } else {
                genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).
                        colorRes(R.color.female_icon).sizeDp(50);
            }
            icon.setImageDrawable(genderIcon);
        }
    }

    private void updatePolylines(String eventID) {
        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();

        DataCache dataCache = DataCache.getInstance();
        Event event = dataCache.getEvents().get(eventID);
        if(markerID.containsValue(eventID)) {
            Person person = dataCache.getPeople().get(event.getPersonID());
            TreeMap<String, Event> events = dataCache.getEvents();
            List<Event> lifeStory = dataCache.orderEvents(events, person);

            if (dataCache.isLifeStory()) {
                int i = 0;
                LatLng startPoint = null;
                for (Event e : lifeStory) {
                    if (i == 0) {
                        startPoint = new LatLng(e.getLatitude(), e.getLongitude());
                    } else {
                        LatLng endPoint = new LatLng(e.getLatitude(), e.getLongitude());
                        Polyline newPolyline = map.addPolyline(new PolylineOptions().add(startPoint)
                                .add(endPoint).color(BLUE).width(8));
                        polylines.add(newPolyline);
                        startPoint = endPoint;
                    }
                    ++i;
                }
            }
            if (dataCache.isFamilyTree()) {
                int width = 16;
                Event e = dataCache.getEvents().get(eventID);
                Person curr = dataCache.getPeople().get(e.getPersonID());
                drawFamilyPolylines(curr, width, e);
            }
            if (dataCache.isSpouseLines()) {
                if(person.getSpouseID() != null) {
                    Person spouse = dataCache.getPeople().get(person.getSpouseID());
                    int smallest = 3000;
                    Event selected = null;
                    for (Map.Entry<String, Event> entry : events.entrySet()) {
                        Event e = entry.getValue();
                        if (e.getPersonID().equals(spouse.getPersonID()) && markerID.containsValue(e.getEventID())
                                && e.getYear() < smallest) {
                            smallest = e.getYear();
                            selected = e;
                        }
                    }
                    if(selected != null) {
                        LatLng startPoint = new LatLng(event.getLatitude(), event.getLongitude());
                        LatLng endPoint = new LatLng(selected.getLatitude(), selected.getLongitude());
                        Polyline newPolyline = map.addPolyline(new PolylineOptions().add(startPoint)
                                .add(endPoint).color(MAGENTA).width(8));
                        polylines.add(newPolyline);
                    }
                }
            }
        }
    }

    private void drawFamilyPolylines(Person currPerson, int width, Event currEvent) {
        DataCache dataCache = DataCache.getInstance();
        Person father = null;
        Person mother = null;
        Event fatherBirth = null;
        Event motherBirth = null;
        if(currEvent == null) {
            return;
        }
        if(currPerson.getFatherID() != null && currPerson.getMotherID() != null) {
            father = dataCache.getPeople().get(currPerson.getFatherID());
            mother = dataCache.getPeople().get(currPerson.getMotherID());

            TreeMap<String,Event> events = dataCache.getEvents();
            int smallestFather = 3000;
            int smallestMother = 3000;
            for(Map.Entry<String, Event> entry : events.entrySet()) {
                Event e = entry.getValue();
                if(e.getPersonID().equals(father.getPersonID()) && markerID.containsValue(e.getEventID())
                        && e.getYear() < smallestFather) {
                    smallestFather = e.getYear();
                    fatherBirth = e;
                }
                if(e.getPersonID().equals(mother.getPersonID()) && markerID.containsValue(e.getEventID())
                        && e.getYear() < smallestMother) {
                    smallestMother = e.getYear();
                    motherBirth = e;
                }
            }

            if(fatherBirth != null) {
                LatLng startPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                LatLng endPoint = new LatLng(fatherBirth.getLatitude(), fatherBirth.getLongitude());
                Polyline newPolyline = map.addPolyline(new PolylineOptions().add(startPoint)
                        .add(endPoint).color(BLACK).width(width));
                polylines.add(newPolyline);
            }

            if(motherBirth != null) {
                LatLng startPoint = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
                LatLng endPoint = new LatLng(motherBirth.getLatitude(), motherBirth.getLongitude());
                Polyline newPolyline = map.addPolyline(new PolylineOptions().add(startPoint)
                        .add(endPoint).color(BLACK).width(width));
                polylines.add(newPolyline);
            }
        } else {
            return;
        }

        width = width - 4;
        drawFamilyPolylines(father, width, fatherBirth);
        drawFamilyPolylines(mother, width, motherBirth);
    }

    private void updateMarkers() {
        DataCache dataCache = DataCache.getInstance();
        TreeMap<String, Event> allEvents = dataCache.getEvents();
        for(Map.Entry<Marker, String> entry : markerID.entrySet()) {
            Marker m = entry.getKey();
            m.remove();
        }
        markerID.clear();

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
            addMarkers(maleUser);
        } else if(maleUser.containsKey(dataCache.getSelectedEvent().getEventID())) {
            dataCache.getSelectedEvent().setEventID("null");
        }
        if(dataCache.isFemaleEvent()) {
            addMarkers(femaleUser);
        } else if(femaleFatherSide.containsKey(dataCache.getSelectedEvent().getEventID())) {
            dataCache.getSelectedEvent().setEventID("null");
        }
        if(dataCache.isMaleEvent() && dataCache.isFatherSide()) {
            addMarkers(maleFatherSide);
        } else if(!dataCache.isMaleEvent()
                && !dataCache.isFatherSide()
                && maleFatherSide.containsKey(dataCache.getSelectedEvent().getEventID())) {
            dataCache.getSelectedEvent().setEventID("null");
        }
        if(dataCache.isFemaleEvent() && dataCache.isFatherSide()) {
            addMarkers(femaleFatherSide);
        } else if(!dataCache.isFemaleEvent()
                && !dataCache.isFatherSide()
                && femaleFatherSide.containsKey(dataCache.getSelectedEvent().getEventID())) {
            dataCache.getSelectedEvent().setEventID("null");
        }
        if(dataCache.isMaleEvent() && dataCache.isMotherSide()) {
            addMarkers(maleMotherSide);
        } else if(!dataCache.isMaleEvent()
                && !dataCache.isMotherSide()
                && maleMotherSide.containsKey(dataCache.getSelectedEvent().getEventID())) {
            dataCache.getSelectedEvent().setEventID("null");
        }
        if(dataCache.isFemaleEvent() && dataCache.isMotherSide()) {
            addMarkers(femaleMotherSide);
        } else if(!dataCache.isFemaleEvent()
                && !dataCache.isMotherSide()
                && femaleMotherSide.containsKey(dataCache.getSelectedEvent().getEventID())) {
            dataCache.getSelectedEvent().setEventID("null");
        }
    }

    private void addMarkers(TreeMap<String,Event> events) {
        for(Map.Entry<String, Event> entry : events.entrySet()) {
            Event e = entry.getValue();
            if(!markerID.containsValue(e.getEventID())) {
                LatLng temp = new LatLng(e.getLatitude(), e.getLongitude());
                Marker marker = map.addMarker(new MarkerOptions().position(temp)
                        .icon(BitmapDescriptorFactory.defaultMarker(getColor(e.getEventType())))
                        .title(e.getCity() + ", " + e.getCountry()));
                markerID.put(marker, e.getEventID());
            }
        }
    }

    private void centerOverSelectedEvent() {
        DataCache dataCache = DataCache.getInstance();
        LatLng eventLatLng = new LatLng(dataCache.getSelectedEventActivity().getLatitude(),
                dataCache.getSelectedEventActivity().getLongitude());

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 3));
    }
}